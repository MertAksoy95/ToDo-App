package com.mertaksoy.todoapp.controller;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.TaskDto;
import com.mertaksoy.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This is the class where we handle Task related requests. API details are available in the service layer.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;


    @Operation(summary = "This is to list all tasks.")
    @GetMapping
    public ResponseEntity<BaseResponse> list(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int size,
                                             @RequestParam(required = false, defaultValue = "desc") String sortDir,
                                             @RequestParam(required = false, defaultValue = "created") String sort,
                                             @RequestParam String categoryId) {
        return taskService.list(page, size, sortDir, sort, categoryId);
    }

    @Operation(summary = "This is to get task by Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "This is when the task is not found.")})
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable String id) {
        return taskService.get(id);
    }

    @Operation(summary = "This is to creating task.")
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @Operation(summary = "This is to updating the task.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "This is when the task is not found.")})
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable String id, @RequestBody TaskDto taskDto) {
        return taskService.update(id, taskDto);
    }

    @Operation(summary = "This is to deleting the task.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "This is when the task is not found.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable String id) {
        return taskService.delete(id);
    }

}
