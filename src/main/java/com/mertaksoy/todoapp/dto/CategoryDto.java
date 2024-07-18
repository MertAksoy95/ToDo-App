package com.mertaksoy.todoapp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDto {

    private String icon;
    private String title;
    private String userId;

}
