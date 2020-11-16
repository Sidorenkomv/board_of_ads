package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;
import com.board_of_ads.models.dto.CategoryDtoMenu;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    Optional<Category> getCategoryByName(String name);

    Category saveCategory(Category category);

    Set<CategoryDto> findAllCategory();

    Optional<CategoryDto> getCategoryDtoById(Long id);

    Category getCategoryById(Long id);

    Optional<CategoryDto> getCategoryDtoByName(String name);

    Category updateCategory(String name, CategoryDto category);

    void deleteCategory(Long id);

    Category createCategory(CategoryDto category);

    List<CategoryDtoMenu> allParentCategory();

    List<CategoryDtoMenu> findChildCatById(Long id);

    Category getCategoryById(Long catId);
}
