package com.hacked.kafkaproducer.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacked.kafkamodel.models.ChangeEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CustomChangeEventListSerializer implements Serializer<List<ChangeEvent>> {

    private final Logger logger = LoggerFactory.getLogger(CustomChangeEventListSerializer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, List<ChangeEvent> changeEvents) {
        try {
            if(changeEvents.isEmpty()) {
                logger.warn("Null Object received");
                return null;
            }
            logger.info("Serializing");
            return objectMapper.writeValueAsBytes(changeEvents);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing ChangeEvents to byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, List<ChangeEvent> data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
