package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.UserDto;
import com.mertaksoy.todoapp.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Slf4j
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class UserTest {

    @Autowired
    private UserService userService;
    private User user;

    @BeforeEach
    void beforeEach() {
        log.info("::: BeforeEach");
        user = userService.getUserByUsernameForTest("testUser");
    }

    @Test
    @Order(1)
    @DisplayName("Create User")
    void givenUserParameters_whenCreateUser_thenStatus200() {
        log.info("::: givenUserParameters_whenCreateUser_thenStatus200");

        UserDto userDto = UserDto.builder()
                .username("testUser")
                .password("test123")
                .firstName("Name")
                .lastName("Lastname")
                .build();

        ResponseEntity<BaseResponse> response = userService.create(userDto);
        assertEquals("Failed 'Create User'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(2)
    @DisplayName("Update User")
    void givenUserParameters_whenUpdateUser_thenStatus200() {
        log.info("::: givenUserParameters_whenUpdateUser_thenStatus200");

        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .firstName("Updated name")
                .lastName("Updated lastname")
                .build();

        ResponseEntity<BaseResponse> response = userService.update(user.getId(), userDto);
        assertEquals("Failed 'Update User'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(3)
    @DisplayName("List Users")
    void givenPaginationParameters_whenListUsers_thenStatus200() {
        log.info("::: givenPaginationParameters_whenListUsers_thenStatus200");

        ResponseEntity<BaseResponse> response = userService.list(0, 10, "DESC", "created");
        assertEquals("Failed 'List Users'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(4)
    @DisplayName("Get User")
    void givenUserId_whenGetUser_thenStatus200() {
        log.info("::: givenUserId_whenGetUser_thenStatus200");

        ResponseEntity<BaseResponse> response = userService.get(user.getId());
        assertEquals("Failed 'Get User'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(5)
    @DisplayName("Delete User")
    void givenUserId_whenDeleteUser_thenStatus200() {
        log.info("::: givenUserId_whenDeleteUser_thenStatus200");

        ResponseEntity<BaseResponse> response = userService.delete(user.getId());
        assertEquals("Failed 'Delete User'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

}
