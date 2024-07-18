package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.CategoryDto;
import com.mertaksoy.todoapp.entity.Category;
import com.mertaksoy.todoapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * This is the class where Category related operations are performed.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService extends BaseService {

    private final ModelMapper mapper;
    private final CategoryRepository categoryRepo;


    /**
     * This lists all categories belonging to the logged in user.
     *
     * @param page    The pagination page
     * @param size    The page size
     * @param sortDir The pagination sort direction
     * @param sort    The pagination sorting parameter
     */
    public ResponseEntity<BaseResponse> list(int page, int size, String sortDir, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", categoryRepo.findAllByUserId(pageRequest, getLoggedInUserId())));
    }

    /**
     * This returns the category based on the given id parameter.
     *
     * @param id Category id
     */
    public ResponseEntity<BaseResponse> get(String id) {
        Category existingCategory = categoryRepo.findById(id).orElse(null);
        if (existingCategory == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No category found for this id: " + id));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", existingCategory));
    }

    /**
     * This creates a new category.
     * Category titles are unique.
     * If there is a category with the same title, it returns a conflict response.
     *
     * @param categoryDto There are title, icon and userId fields for the category.
     */
    public ResponseEntity<BaseResponse> create(CategoryDto categoryDto) {
        Category existingCategory = categoryRepo.findByTitleAndUserId(categoryDto.getTitle(), getLoggedInUserId());
        if (existingCategory != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new BaseResponse("This category title is already in use: " + categoryDto.getTitle()));
        }

        Category category = mapper.map(categoryDto, Category.class);
        category.setUserId(getLoggedInUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", categoryRepo.save(category)));
    }

    /**
     * This updates the existing category.
     * Category titles are unique.
     * If there is a category with the same title, it returns a conflict response.
     *
     * @param id          Category id
     * @param categoryDto There are title and icon fields for the category.
     */
    public ResponseEntity<BaseResponse> update(String id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepo.findById(id).orElse(null);
        if (existingCategory == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No category found for this id: " + id));
        }

        if (!existingCategory.getTitle().equalsIgnoreCase(categoryDto.getTitle())) {
            Category category = categoryRepo.findByTitleAndUserId(categoryDto.getTitle(), getLoggedInUserId());
            if (category != null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new BaseResponse("This category title is already in use: " + categoryDto.getTitle()));
            }
        }

        existingCategory.setTitle(categoryDto.getTitle());
        existingCategory.setIcon(categoryDto.getIcon());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", categoryRepo.save(existingCategory)));
    }

    /**
     * It deletes the category belonging to the given category id.
     *
     * @param id Category id
     */
    public ResponseEntity<BaseResponse> delete(String id) {
        Category existingCategory = categoryRepo.findById(id).orElse(null);
        if (existingCategory == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No category found for this id: " + id));
        }

        categoryRepo.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful"));
    }

    public Category getCategoryByTitleForTest(String title) {
        Category category = categoryRepo.findByTitle(title);
        log.info(":::::: category: {}", category != null ? category.toString() : null);
        return category;
    }

}
