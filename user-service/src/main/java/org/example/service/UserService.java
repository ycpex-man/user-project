package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dtos.CreateUserRequest;
import org.example.dtos.UserDto;
import org.example.entity.User;
import org.example.mappers.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository repository;

    public UserDto createUser(CreateUserRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new RuntimeException("Email уже существует");
        }
        User user = new User(request.name(), request.email(), request.age());
        return userMapper.toDto(repository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(int id) {
        return repository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public UserDto updateUser(int id, String email) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (repository.existsByEmail(email)) {
            throw new RuntimeException("Email уже существует");
        }
        user.setEmail(email);
        return userMapper.toDto(repository.save(user));
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
