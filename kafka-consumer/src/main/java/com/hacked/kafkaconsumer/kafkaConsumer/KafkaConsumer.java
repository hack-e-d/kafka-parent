package com.hacked.kafkaconsumer.kafkaConsumer;

import com.hacked.kafkaconsumer.helper.UserHistoryHelper;
import com.hacked.kafkamodel.models.ChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final String TOPIC_NAME = "kafka-poc-topic-custom";

    @Autowired
    private UserHistoryHelper userHistoryHelper;

    @KafkaListener(topics = TOPIC_NAME, groupId = "your-consumer-group-id")
    public void consumeMessage(List<HashMap<String, String>> messages) {
        logger.info("Incoming events : " + messages);
        userHistoryHelper.saveUserHistory(messages);
    }
}
