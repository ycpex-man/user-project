package org.example.service;

import org.example.dtos.CreateUserRequest;
import org.example.dtos.UserDto;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Создание нового пользователя должно корректно сохранять его в репозитории")
    void testCreateUser(){
        CreateUserRequest testUser = createUserRequest();
        when(userRepository.save(any(User.class))).thenReturn(createTestUser());
        userService.createUser(testUser);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User caughtUser = captor.getValue();
        assertEquals(testUser.name(), caughtUser.getName());
        assertEquals(testUser.email(), caughtUser.getEmail());
        assertEquals(testUser.age(), caughtUser.getAge());
    }

    @Test
    @DisplayName("Метод getAllUsers должен возвращать всех пользователей из репозитория")
    void testGetAllUsers(){
        List<User> testList = listOfUsers();
        when(userRepository.findAll()).thenReturn(testList);
        List<UserDto> users = userService.getAllUsers();
        verify(userRepository).findAll();
        assertEquals(2, testList.size(), "Должно быть два пользователя в списке");
        assertEquals(users.get(0).getName(), testList.get(0).getName());
        assertEquals(users.get(0).getEmail(), testList.get(0).getEmail());
        assertEquals(users.get(0).getAge(), testList.get(0).getAge());
        assertEquals(users.get(1).getName(), testList.get(1).getName());
        assertEquals(users.get(1).getEmail(), testList.get(1).getEmail());
        assertEquals(users.get(1).getAge(), testList.get(1).getAge());
    }

    @Test
    @DisplayName("Метод getUserById должен возвращать пользователя по его ID")
    void testGetUserById(){
        User testUser = createTestUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        UserDto caughtUser = userService.getUserById(1);
        verify(userRepository).findById(1);
        assertEquals(testUser.getName(), caughtUser.getName());
        assertEquals(testUser.getEmail(), caughtUser.getEmail());
        assertEquals(testUser.getAge(), caughtUser.getAge());
    }

    @Test
    @DisplayName("Метод updateUser должен обновлять email существующего пользователя")
    void testUpdateUser(){
        User testUser = createTestUser();
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        UserDto caughtUserDto = userService.updateUser(1, "ycpex-man@yandex.ru");
        verify(userRepository).save(testUser);
        assertEquals(testUser.getName(), caughtUserDto.getName());
        assertEquals(testUser.getEmail(), caughtUserDto.getEmail());
        assertEquals(testUser.getAge(), caughtUserDto.getAge());
    }

    @Test
    @DisplayName("Метод deleteUser должен вызывать deleteById репозитория для указанного ID")
    void testDeleteUser(){
        userService.deleteUser(1);
        verify(userRepository).deleteById(1);
    }

    private CreateUserRequest createUserRequest(){
        return new CreateUserRequest("Alexey", "lekha-kovalev-02@mail.ru", 23);
    }

    private UserDto createTestUserDto(){
        UserDto userDto = new UserDto();
        userDto.setName("Alexey");
        userDto.setEmail("lekha-kovalev-02@mail.ru");
        userDto.setAge(23);
        return userDto;
    }

    private User createTestUser(){
        return User.builder()
                .id(1)
                .name("Alexey")
                .email("lekha-kovalev-02@mail.ru")
                .age(23)
                .createdAt(LocalDate.of(2025, 9, 12))
                .build();
    }

    private List<User> listOfUsers(){
        User user1 = User.builder()
                .id(1)
                .name("Alexey")
                .email("lekha-kovalev-02@mail.ru")
                .age(23)
                .createdAt(LocalDate.of(2025, 9, 12))
                .build();
        User user2 = User.builder()
                .id(2)
                .name("Varvara")
                .email("var05@mail.ru")
                .age(24)
                .createdAt(LocalDate.of(2025, 9, 12))
                .build();
        return List.of(user1, user2);
    }
}
