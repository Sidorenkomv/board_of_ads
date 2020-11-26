package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;
import com.board_of_ads.models.dto.CategoryDtoMenu;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public Response<Set<CategoryDto>> findAll() {
        log.info("Use CategoryRestController.findAll() method");

        var categories = categoryService.findAllCategory();
        return (categories.size() > 0)
                ? Response.ok(categories)
                : new ErrorResponse<>(new Error(204, "No found categories"));
    }

    @GetMapping("/allParentCategory")
    public Response<List<CategoryDtoMenu>> findAllParentCategory() {
        log.info("Use CategoryRestController.findAllParentCategory() method");
        return Response.ok(categoryService.allParentCategory());
    }

    @GetMapping("/allChildCategories/{id}")
    public Response<List<CategoryDtoMenu>> findAllChildCategoryByParentId(@PathVariable Long id) {
        List<CategoryDtoMenu> category = categoryService.findChildCatById(id);
        log.info("CategoryRestController.findAllChildCategoryByParentId() method worked");
        return category != null
                ? Response.ok(category)
                : new ErrorResponse<>(new Error(204, "No found category"));
    }

    @GetMapping("/{id}")
    public Response<CategoryDto> findById(@PathVariable Long id) {
        var category = categoryService.getCategoryDtoById(id);
        return (category.isPresent())
                ? Response.ok(category.get())
                : new ErrorResponse<>(new Error(204, "No found category"));
    }

    @GetMapping ("/getByName/{categoryName}")
    public Response <CategoryDto> getCategoryByName (@PathVariable String categoryName) {
        var category = categoryService.getCategoryDtoByName(categoryName);
        log.info("CategoryRestController.getCategoryByName() worked with name " + categoryName);
        return (category.isPresent())
                ? Response.ok(category.get())
                : new ErrorResponse<>(new Error(204, "Category by name " + categoryName + " not found"));
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
