package org.example.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/*
@Data
public class UpdateUserEmailRequest {
    @Schema(description = "Новый email пользователя для изменения", example = "newEmail@intensiv.ru")
    private String email;
}
 */
public record UpdateUserEmailRequest(@Schema(description = "Новый email пользователя для изменения",
                                             example = "newEmail@intensiv.ru") String email){

}