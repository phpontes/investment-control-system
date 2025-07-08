package com.itau.investments.investmentcontrolsystem.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.investments.investmentcontrolsystem.dto.CotacaoKafkaDTO;
import com.itau.investments.investmentcontrolsystem.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CotacaoConsumer {

    private final CotacaoService cotacaoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "cotacoes", groupId = "cotacao-consumer-group")
    public void consumir(ConsumerRecord<String, String> record) {
        try {
            String mensagem = record.value();
            CotacaoKafkaDTO dto = objectMapper.readValue(mensagem, CotacaoKafkaDTO.class);
            cotacaoService.registrarCotacao(dto.getAtivoId(), dto.getPrecoUnitario());
            log.info("Cotação processada com sucesso: {}", dto);
        } catch (Exception e) {
            log.error("Erro ao processar mensagem Kafka", e);
        }
    }
}
