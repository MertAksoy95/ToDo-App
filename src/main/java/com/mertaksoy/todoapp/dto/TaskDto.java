package com.mertaksoy.todoapp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskDto {

    private String description;
    private TaskStatus status;
    private LocalDateTime expiryDate;
    private boolean favorite;
    private String categoryId;

}
