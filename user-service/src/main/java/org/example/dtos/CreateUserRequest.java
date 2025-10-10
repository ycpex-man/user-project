package org.example.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserRequest(@Schema(description = "Имя пользователя", example = "Алексей") String name,
                                @Schema(description = "Email пользователя", example = "leshka.kovalev.02@gmail.com") String email,
                                @Schema(description = "Возраст пользователя", example = "23")Integer age) {
}
