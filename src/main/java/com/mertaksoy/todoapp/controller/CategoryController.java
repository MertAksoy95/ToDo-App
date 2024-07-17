package com.mertaksoy.todoapp.controller;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.CategoryDto;
import com.mertaksoy.todoapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This is the class where we handle category related requests. API details are available in the service layer.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @Operation(summary = "This lists all categories belonging to the logged in user.")
    @GetMapping
    public ResponseEntity<BaseResponse> list(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int size,
                                             @RequestParam(required = false, defaultValue = "desc") String sortDir,
                                             @RequestParam(required = false, defaultValue = "created") String sort) {
        return categoryService.list(page, size, sortDir, sort);
    }

    @Operation(summary = "This is to get category by Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "This is when the category is not found.")})
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable String id) {
        return categoryService.get(id);
    }

    @Operation(summary = "This is to creating category.")
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @Operation(summary = "This is to updating the category.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "This is when the category is not found.")})
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable String id, @RequestBody CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @Operation(summary = "This is to deleting the category.")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "This is when the category is not found.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable String id) {
        return categoryService.delete(id);
    }

}
