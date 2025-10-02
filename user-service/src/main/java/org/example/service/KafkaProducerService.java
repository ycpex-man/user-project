package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserEvent(String operation, String name, String email){
        String message = String.format("{\"operation\":\"%s\",\"name\":\"%s\",\"email\":\"%s\"}", operation, name,email);
        kafkaTemplate.send("events", message);
    }
}
