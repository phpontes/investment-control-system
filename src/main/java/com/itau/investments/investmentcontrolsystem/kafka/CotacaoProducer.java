package com.itau.investments.investmentcontrolsystem.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.investments.investmentcontrolsystem.dto.CotacaoKafkaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CotacaoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void enviarCotacao(CotacaoKafkaDTO dto) {
        try {
            String mensagemJson = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("cotacoes", mensagemJson);
            log.info("Mensagem enviada ao Kafka: {}", mensagemJson);
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter CotacaoKafkaDTO para JSON", e);
        }
    }
}
