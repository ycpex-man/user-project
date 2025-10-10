package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dtos.CreateUserRequest;
import org.example.dtos.UpdateUserEmailRequest;
import org.example.dtos.UserDto;
import org.example.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Users", description = "API для работы с пользователями")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить всех пользователей", description = "")
    @GetMapping
    public CollectionModel<UserDto> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        users.forEach(user -> {
            user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
            user.add(linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update"));
            user.add(linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete"));
                }
        );
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).createUser(null)).withRel("create"),
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public UserDto getUser(@Parameter(description = "ID пользователя") @PathVariable int id) {
        UserDto user = userService.getUserById(id);
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAll"));
        user.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).createUser(null)).withRel("create"));
        user.add(linkTo(methodOn(UserController.class).updateUser(id, null)).withRel("update"));
        user.add(linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete"));
        return user;
    }

    @Operation(summary = "Добавить нового пользователя", description = "Добавляет нового пользователя и возвращает DTO")
    @PostMapping
    public UserDto createUser(@RequestBody CreateUserRequest request) {
        UserDto user = userService.createUser(request);
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAll"));
        user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).createUser(null)).withRel("create"));
        user.add(linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update"));
        user.add(linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete"));
        return user;
    }

    @Operation(summary = "Изменить email пользователя", description = "Находит пользователя по ID и изменяет email на введённый")
    @PatchMapping("/{id}")
    public UserDto updateUser(@Parameter(description = "ID пользователя")@PathVariable int id, @RequestBody UpdateUserEmailRequest request) {
        UserDto user = userService.updateUser(id, request.email());
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAll"));
        user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).createUser(null)).withRel("create"));
        user.add(linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update"));
        user.add(linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete"));
        return user;
    }

    @Operation(summary = "Удалить пользователя", description = "Находит пользователя по ID и удаляет")
    @DeleteMapping("/{id}")
    public UserDto deleteUser(@Parameter(description = "ID пользователя")@PathVariable int id) {
        UserDto user = userService.getUserById(id);
        userService.deleteUser(id);
        user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAll"));
        user.add(linkTo(methodOn(UserController.class).createUser(null)).withRel("create"));
        return user;
    }
}
