package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//Из-за Swagger маппер работает только если явно прописать геттеры и сеттеры, поэтому не через Lombok
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private Integer age;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @PrePersist
    void onCreate(){
        this.createdAt = LocalDate.now();
    }

    public User(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

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
