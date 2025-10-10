package org.example.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EmailTemplateService emailTemplateService;
    private final MailService mailService;

    @CircuitBreaker(name = "notificationServiceCircuit", fallbackMethod = "fallbackConsume")
    @KafkaListener(topics = "events")
    public void consume(@Valid UserEvent userEvent)  {
        String subject =  emailTemplateService.getSubject(userEvent);
        String text =  emailTemplateService.getText(userEvent);
        mailService.send(userEvent.email(), subject, text);
    }

    public void fallbackConsume(UserEvent userEvent, Throwable throwable){
        System.out.println("Fallback : " + throwable.getMessage());
    }
}
