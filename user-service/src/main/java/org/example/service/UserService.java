package org.example.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.dtos.CreateUserRequest;
import org.example.dtos.UserDto;
import org.example.entity.User;
import org.example.mappers.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository repository;

    @CircuitBreaker(name = "userServiceCircuit", fallbackMethod = "fallbackCreateUser")
    public UserDto createUser(CreateUserRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new RuntimeException("Email уже существует");
        }
        User user = new User(request.name(), request.email(), request.age());
        return userMapper.toDto(repository.save(user));
    }

    @CircuitBreaker(name = "userServiceCircuit", fallbackMethod = "fallbackGetAll")
    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "userServiceCircuit", fallbackMethod = "fallbackGetById")
    public UserDto getUserById(int id) {
        return repository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @CircuitBreaker(name = "userServiceCircuit", fallbackMethod = "fallbackUpdate")
    public UserDto updateUser(int id, String email) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (repository.existsByEmail(email)) {
            throw new RuntimeException("Email уже существует");
        }
        user.setEmail(email);
        return userMapper.toDto(repository.save(user));
    }

    @CircuitBreaker(name = "userServiceCircuit", fallbackMethod = "fallbackDelete")
    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    public UserDto fallbackCreateUser(CreateUserRequest request, Throwable throwable){
        System.out.println("Fallback : " + throwable.getMessage());
        return null;
    }


    public List<UserDto> fallbackGetAll(Throwable throwable){
        System.out.println("Fallback : " + throwable.getMessage());
        return Collections.emptyList();
    }

    public UserDto fallbackGetById(int id, Throwable throwable){
        System.out.println("Fallback : " + throwable.getMessage());
        return null;
    }

    public UserDto fallbackUpdate(int id,  String email, Throwable throwable){
        System.out.println("Fallback : " + throwable.getMessage());
        return null;
    }

    public void fallbackDelete(int id, Throwable throwable){
        System.out.println("Fallback : " + throwable.getMessage());
    }
}
