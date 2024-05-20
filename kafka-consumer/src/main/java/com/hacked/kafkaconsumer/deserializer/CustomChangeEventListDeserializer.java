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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomChangeEventListDeserializer implements Deserializer<List<ChangeEvent>> {

    private final Logger logger = LoggerFactory.getLogger(CustomChangeEventListDeserializer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public List<ChangeEvent> deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                logger.warn("Null byte stream received.");
                return null;
            }
            logger.info("Deserializing...");
            List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
            changeEvents = objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), changeEvents.getClass());
            return changeEvents;
        } catch (Exception e) {
            throw  new SerializationException("Error when deserializing byte[] to ChangeEvent");
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
