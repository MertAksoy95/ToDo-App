package com.mertaksoy.todoapp.entity;

import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;

@Data
@Document
public class Category extends BaseEntity {

    private String icon;
    private String title;
    private String userId;

}
