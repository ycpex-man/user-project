package org.example.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

//Из-за Swagger маппер работает только если явно прописать геттеры и сеттеры, поэтому не через Lombok
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {

    @Schema(description = "Уникальный код пользователя(Создаётся автоматически)", example = "1")
    private Integer id;
    @Schema(description = "Имя пользователя", example = "Алексей")
    private String name;
    @Schema(description = "Email пользователя", example = "leshkaa.kovalev.02@gmail.com")
    private String email;
    @Schema(description = "Возраст пользователя", example = "23")
    private Integer age;
    @Schema(description = "Дата создания пользователя в БД(Создаётся автоматически)", example = "2025-08-31")
    private LocalDate createdAt;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
