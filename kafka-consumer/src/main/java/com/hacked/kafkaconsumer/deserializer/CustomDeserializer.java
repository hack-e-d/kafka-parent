package com.hacked.kafkaconsumer.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacked.kafkamodel.models.ChangeEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomDeserializer implements Deserializer<ChangeEvent> {

    private final Logger logger = LoggerFactory.getLogger(CustomDeserializer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ChangeEvent deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                logger.warn("Null byte stream received.");
                return null;
            }
            logger.info("Deserializing...");
            return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), ChangeEvent.class);
        } catch (Exception e) {
            throw  new SerializationException("Error when deserializing byte[] to ChangeEvent");
        }
    }

    @Override
    public ChangeEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public ChangeEvent deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
