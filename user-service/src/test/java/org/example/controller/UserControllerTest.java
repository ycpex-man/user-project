package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dtos.CreateUserRequest;
import org.example.dtos.UpdateUserEmailRequest;
import org.example.dtos.UserDto;
import org.example.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /api/users - должно возвращать всех пользователей")
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(listOfUsersDto());
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alexey"))
                .andExpect(jsonPath("$[0].email").value("lekha-kovalev-02@mail.ru"))
                .andExpect(jsonPath("$[0].age").value(23))
                .andExpect(jsonPath("$[1].name").value("Varvara"))
                .andExpect(jsonPath("$[1].email").value("var05@mail.ru"))
                .andExpect(jsonPath("$[1].age").value(24));
    }

    @Test
    @DisplayName("GET /api/users/{id} - должно возвращать пользователя по ID")
    void testGetUser() throws Exception {
        UserDto testUser = createTestUserDto();
        when(userService.getUserById(1)).thenReturn(testUser);
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(testUser.getName()))
                .andExpect(jsonPath("email").value(testUser.getEmail()))
                .andExpect(jsonPath("age").value(testUser.getAge()));
    }

    @Test
    @DisplayName("POST /api/users - должно создавать нового пользователя")
    void testCreateUser() throws Exception {
        UserDto testUser = createTestUserDto();
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(testUser);
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(jsonPath("name").value(testUser.getName()))
                .andExpect(jsonPath("email").value(testUser.getEmail()))
                .andExpect(jsonPath("age").value(testUser.getAge()));
    }

    @Test
    @DisplayName("PATCH /api/users/{id} - должно обновлять email пользователя")
    void testUpdateUser() throws Exception {
        UpdateUserEmailRequest request = new UpdateUserEmailRequest("ycpex-man@yandex.ru");
        UserDto testUser = createTestUserDto();
        testUser.setEmail("ycpex-man@yandex.ru");
        when(userService.updateUser(1, "ycpex-man@yandex.ru")).thenReturn(testUser);
        mockMvc.perform(patch("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(testUser.getName()))
                .andExpect(jsonPath("email").value(testUser.getEmail()))
                .andExpect(jsonPath("age").value(testUser.getAge()));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - должно удалять пользователя")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(1);
    }

    private UserDto createTestUserDto(){
        UserDto userDto = new UserDto();
        userDto.setName("Alexey");
        userDto.setEmail("lekha-kovalev-02@mail.ru");
        userDto.setAge(23);
        return userDto;
    }

    private List<UserDto> listOfUsersDto(){
        UserDto userDto1 = new UserDto();
                userDto1.setName("Alexey");
                userDto1.setEmail("lekha-kovalev-02@mail.ru");
                userDto1.setAge(23);
        UserDto userDto2 = new UserDto();
                userDto2.setName("Varvara");
                userDto2.setEmail("var05@mail.ru");
                userDto2.setAge(24);
        return List.of(userDto1, userDto2);
    }

}
