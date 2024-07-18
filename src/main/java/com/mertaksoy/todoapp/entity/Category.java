package com.mertaksoy.todoapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Category extends BaseEntity {

    private String icon;
    private String title;
    private String userId;

}
