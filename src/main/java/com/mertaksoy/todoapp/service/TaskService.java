package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.TaskDto;
import com.mertaksoy.todoapp.dto.TaskStatus;
import com.mertaksoy.todoapp.entity.Task;
import com.mertaksoy.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This is the class where Task related operations are performed.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {

    private final ModelMapper mapper;
    private final TaskRepository taskRepo;


    /**
     * This lists all tasks.
     *
     * @param page    The pagination page
     * @param size    The page size
     * @param sortDir The pagination sort direction
     * @param sort    The pagination sorting parameter
     */
    public ResponseEntity<BaseResponse> list(int page, int size, String sortDir, String sort, String categoryId) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", taskRepo.findAllByCategoryId(pageRequest, categoryId)));
    }

    /**
     * This returns the task based on the given id parameter.
     *
     * @param id Task id
     */
    public ResponseEntity<BaseResponse> get(String id) {
        Task existingTask = taskRepo.findById(id).orElse(null);
        if (existingTask == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No task found for this id: " + id));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", existingTask));
    }

    /**
     * This creates a new task.
     *
     * @param taskDto There are description, expiryDate, favorite and categoryId fields for the task.
     */
    public ResponseEntity<BaseResponse> create(TaskDto taskDto) {
        Task task = mapper.map(taskDto, Task.class);
        task.setStatus(TaskStatus.TODO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", taskRepo.save(task)));
    }

    /**
     * This updates the existing task.
     *
     * @param id      Task id
     * @param taskDto There are description, expiryDate, status and favorite fields for the task.
     */
    public ResponseEntity<BaseResponse> update(String id, TaskDto taskDto) {
        Task existingTask = taskRepo.findById(id).orElse(null);
        if (existingTask == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No task found for this id: " + id));
        }

        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setExpiryDate(taskDto.getExpiryDate());
        existingTask.setFavorite(taskDto.isFavorite());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", taskRepo.save(existingTask)));
    }

    /**
     * It deletes the task belonging to the given task id.
     *
     * @param id Task id
     */
    public ResponseEntity<BaseResponse> delete(String id) {
        Task existingTask = taskRepo.findById(id).orElse(null);
        if (existingTask == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No task found for this id: " + id));
        }

        taskRepo.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful"));
    }

}
