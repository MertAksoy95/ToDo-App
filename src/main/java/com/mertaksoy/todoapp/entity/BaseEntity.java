package com.mertaksoy.todoapp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Schema(readOnly = true)
    @CreatedDate
    private LocalDateTime created;

    @Schema(readOnly = true)
    @LastModifiedDate
    private LocalDateTime updated;


}
