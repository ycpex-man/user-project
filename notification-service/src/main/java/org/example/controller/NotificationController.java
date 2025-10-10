package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.UserEvent;
import org.example.service.EmailTemplateService;
import org.example.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailTemplateService emailTemplateService;
    private final MailService mailService;

    @PostMapping
    public String sendMessage(@Valid @RequestBody UserEvent userEvent){
        String subject =  emailTemplateService.getSubject(userEvent);
        String text =  emailTemplateService.getText(userEvent);
        try{
            mailService.send(userEvent.email(), subject, text);
            return ResponseEntity.ok().toString();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).toString();
        }
    }
}
