package com.mertaksoy.todoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private String description;
    private TaskStatus status;
    private LocalDateTime expiryDate;
    private boolean favorite;
    private String categoryId;

}
