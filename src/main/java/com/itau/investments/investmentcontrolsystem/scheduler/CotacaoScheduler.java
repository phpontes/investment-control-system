package com.itau.investments.investmentcontrolsystem.scheduler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.itau.investments.investmentcontrolsystem.dto.CotacaoKafkaDTO;
import com.itau.investments.investmentcontrolsystem.kafka.CotacaoProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CotacaoScheduler {

    private final CotacaoProducer cotacaoProducer;

    private final List<String> ativosMonitorados = List.of("PETR4", "VALE3", "ITUB4");

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    @Scheduled(fixedRate = 60000)
    public void consultarEEnviarCotações() {
        for (String codigoAtivo : ativosMonitorados) {
            try {
                Map<String, Object> response = restTemplate.getForObject(
                        "https://b3api.vercel.app/rest/quote?symbol={codigo}",
                        Map.class, codigoAtivo);

                if (response == null || !response.containsKey("regularMarketPrice")) {
                    log.warn("Resposta incompleta ou inválida para {}", codigoAtivo);
                    continue;
                }

                BigDecimal preco = new BigDecimal(response.get("regularMarketPrice").toString());

                CotacaoKafkaDTO dto = new CotacaoKafkaDTO();
                dto.setAtivoId(resolverAtivoId(codigoAtivo));
                dto.setPrecoUnitario(preco);
                dto.setDataHora(LocalDateTime.now());

                cotacaoProducer.enviarCotacao(dto);
                log.info("Enviada cotação para Kafka: {}", dto);
                
            } catch (HttpClientErrorException.NotFound nf) {
                log.warn("Código {} não encontrado na API da B3 (404)", codigoAtivo);
            } catch (Exception e) {
                log.error("Erro ao buscar cotação para " + codigoAtivo, e);
            }
        }
    }

    private Long resolverAtivoId(String codigo) {
        return switch (codigo) {
            case "PETR4" -> 1L;
            case "VALE3" -> 2L;
            case "ITUB4" -> 3L;
            default -> throw new IllegalArgumentException("Código não mapeado: " + codigo);
        };
    }
}
