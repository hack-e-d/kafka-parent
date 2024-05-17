package com.hacked.kafkaproducer.controller;

import com.hacked.kafkamodel.models.ChangeEvent;
import com.hacked.kafkaproducer.kafkaProducer.KafkaProducer;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    private final KafkaProducer kafkaProducer;

    private static final Logger logger = LoggerFactory.getLogger(SendController.class);

    public SendController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/sendMessage")
    public String publishToKafkaTopic(@NotNull @RequestBody ChangeEvent changeEvent) {
        logger.info("Trying to send Change Event through Kafka Topic......");
        kafkaProducer.sendMessage(changeEvent);
        return "Sent to topic";
    }
}
