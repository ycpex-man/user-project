package org.example.service;

import org.example.dtos.UserEvent;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String getSubject(UserEvent userEvent){
        String operation = userEvent.operation();
        return switch (operation.toUpperCase()) {
            case "CREATE" -> "Аккаунт создан";
            case "DELETE" -> "Аккаунт удалён";
            default -> throw new IllegalArgumentException("Неизвестная операция: " + userEvent.operation());
        };
    }

    public String getText(UserEvent userEvent){
        String operation = userEvent.operation();
        return switch (operation.toUpperCase()) {
            case "CREATE" -> "Здравствуйте " + userEvent.name() + "! Ваш аккаунт на сайте был успешно создан.";
            case "DELETE" -> "Здравствуйте " + userEvent.name() + "! Ваш аккаунт был удалён.";
            default -> throw new IllegalArgumentException("Неизвестная операция: " + userEvent.operation());
        };
    }
}
