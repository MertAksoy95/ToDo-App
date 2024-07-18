package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.TaskDto;
import com.mertaksoy.todoapp.entity.Task;
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
class TaskTest {

    @Autowired
    private TaskService taskService;
    private Task task;

    @BeforeEach
    void beforeEach() {
        log.info("::: BeforeEach");
        task = taskService.getTaskByDescriptionForTest("testTask");
    }

    @Test
    @Order(1)
    @DisplayName("Create Task")
    void givenTaskParameters_whenCreateTask_thenStatus200() {
        log.info("::: givenTaskParameters_whenCreateTask_thenStatus200");

        TaskDto taskDto = TaskDto.builder()
                .description("testTask")
                .expiryDate(null)
                .favorite(false)
                .build();

        ResponseEntity<BaseResponse> response = taskService.create(taskDto);
        assertEquals("Failed 'Create Task'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(2)
    @DisplayName("Update Task")
    void givenTaskParameters_whenUpdateTask_thenStatus200() {
        log.info("::: givenTaskParameters_whenUpdateTask_thenStatus200");

        TaskDto taskDto = TaskDto.builder()
                .description("testTask")
                .expiryDate(null)
                .favorite(false)
                .build();

        ResponseEntity<BaseResponse> response = taskService.update(task.getId(), taskDto);
        assertEquals("Failed 'Update Task'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(3)
    @DisplayName("List Tasks")
    void givenPaginationParameters_whenListTasks_thenStatus200() {
        log.info("::: givenPaginationParameters_whenListTasks_thenStatus200");

        ResponseEntity<BaseResponse> response = taskService.list(0, 10, "DESC", "created", "80dbddae-7a7e-407f-9674-ffc45469a228");
        assertEquals("Failed 'List Tasks'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(4)
    @DisplayName("Get Task")
    void givenTaskId_whenGetTask_thenStatus200() {
        log.info("::: givenTaskId_whenGetTask_thenStatus200");

        ResponseEntity<BaseResponse> response = taskService.get(task.getId());
        assertEquals("Failed 'Get Task'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(5)
    @DisplayName("Delete Task")
    void givenTaskId_whenDeleteTask_thenStatus200() {
        log.info("::: givenTaskId_whenDeleteTask_thenStatus200");

        ResponseEntity<BaseResponse> response = taskService.delete(task.getId());
        assertEquals("Failed 'Delete Task'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

}
