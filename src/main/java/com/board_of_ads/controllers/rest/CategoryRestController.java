package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public Response<Set<CategoryDto>> findAll() {
        log.info("Use this default logger");

        var categories = categoryService.findAllCategory();
        return (categories.size() > 0)
                ? Response.ok(categories)
                : new ErrorResponse<>(new Error(204, "No found categories"));
    }

    @GetMapping("/{id}")
    public Response<CategoryDto> findById(@PathVariable Long id) {
        var category = categoryService.getCategoryDtoById(id);
        return (category.isPresent())
                ? Response.ok(category.get())
                : new ErrorResponse<>(new Error(204, "No found category"));
    }

   @PutMapping("/{old}")
    public Response<Category> setCategory(@PathVariable(name = "old") String old, @RequestBody CategoryDto category) {
        var result = categoryService.updateCategory(old, category);
        log.info("Updated category is : {}", result);
        return result != null
                ? Response.ok(result)
                : new Response.ErrorBuilderImpl().code(204).text("No update category").build();
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteCategoryId(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return new Response<>();
    }

    @PostMapping
    public Response<Void> createCategory(@RequestBody CategoryDto category) {
        categoryService.createCategory(category);
        return new Response<>();
    }
}
