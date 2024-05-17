package com.hacked.kafkaproducer.kafkaProducer;


import com.hacked.kafkamodel.models.ChangeEvent;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTemplate<String, ChangeEvent> customKafkaTemplate;

    private final String TOPIC_NAME = "kafka-poc-topic";

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, ChangeEvent> customKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.customKafkaTemplate = customKafkaTemplate;
    }

    @Deprecated
//    moving to custom serializer implementation
    public void sendMessage(@NotNull List<ChangeEvent> changeEvents) {
        kafkaTemplate.send(TOPIC_NAME, UUID.randomUUID().toString(), changeEvents.toString());
        logger.info("Trying to send data : " + changeEvents + " On topic : " + TOPIC_NAME.toUpperCase());
    }

    public void sendMessage(@NotNull ChangeEvent changeEvent) {
        customKafkaTemplate.send(TOPIC_NAME, UUID.randomUUID().toString(), changeEvent);
        logger.info("Trying to send Change Event : " + changeEvent + " On topic : " + TOPIC_NAME.toUpperCase());
    }
}
