package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.CategoryDto;
import com.mertaksoy.todoapp.entity.Category;
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
class CategoryTest {

    @Autowired
    private CategoryService categoryService;
    private Category category;

    @BeforeEach
    void beforeEach() {
        log.info("::: BeforeEach");
        category = categoryService.getCategoryByTitleForTest("testCategory");
    }

    @Test
    @Order(1)
    @DisplayName("Create Category")
    void givenCategoryParameters_whenCreateCategory_thenStatus200() {
        log.info("::: givenCategoryParameters_whenCreateCategory_thenStatus200");

        CategoryDto categoryDto = CategoryDto.builder()
                .title("testCategory")
                .icon("EditOutlined")
                .build();

        ResponseEntity<BaseResponse> response = categoryService.create(categoryDto);

        log.info(":::::: RESPONSE: {}", response.toString());
        assertEquals("Failed 'Create Category'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(2)
    @DisplayName("Update Category")
    void givenCategoryParameters_whenUpdateCategory_thenStatus200() {
        log.info("::: givenCategoryParameters_whenUpdateCategory_thenStatus200");

        CategoryDto categoryDto = CategoryDto.builder()
                .icon("DeleteOutlined")
                .title(category.getTitle())
                .build();

        ResponseEntity<BaseResponse> response = categoryService.update(category.getId(), categoryDto);
        assertEquals("Failed 'Update Category'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(3)
    @DisplayName("List Categories")
    void givenPaginationParameters_whenListCategories_thenStatus200() {
        log.info("::: givenPaginationParameters_whenListCategories_thenStatus200");

        ResponseEntity<BaseResponse> response = categoryService.list(0, 10, "DESC", "created");
        assertEquals("Failed 'List Categories'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(4)
    @DisplayName("Get Category")
    void givenCategoryId_whenGetCategory_thenStatus200() {
        log.info("::: givenCategoryId_whenGetCategory_thenStatus200");

        ResponseEntity<BaseResponse> response = categoryService.get(category.getId());
        assertEquals("Failed 'Get Category'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

    @Test
    @Order(5)
    @DisplayName("Delete Category")
    void givenCategoryId_whenDeleteCategory_thenStatus200() {
        log.info("::: givenCategoryId_whenDeleteCategory_thenStatus200");

        ResponseEntity<BaseResponse> response = categoryService.delete(category.getId());
        assertEquals("Failed 'Delete Category'", HttpStatus.OK.toString(), response.getStatusCode().toString());
    }

}
