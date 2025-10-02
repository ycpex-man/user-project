package org.example.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserEvent(@NotBlank(message = "Операция пользователя обязательна") String operation,
                        @NotBlank(message = "Email пользователя обязателен") String email,
                        @NotBlank(message = "Имя обязательно") String name) {
}
