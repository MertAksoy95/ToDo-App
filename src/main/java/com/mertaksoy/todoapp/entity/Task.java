package com.mertaksoy.todoapp.entity;

import com.mertaksoy.todoapp.dto.TaskStatus;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Task extends BaseEntity {

    private String description;
    private TaskStatus status;
    private LocalDateTime expiryDate;
    private boolean favorite;
    private String categoryId;

}
