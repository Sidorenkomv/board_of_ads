package com.board_of_ads.repository;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDtoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String categoryName);

    Category findCategoryById(Long id);


    @Query("from Category c where c.category.id = :id")
    List<Category> findCategoriesByCategory(Long id);

    @Query("from Category c where c.name like :name")
    List<Category> findParentLikeName(String name);

    @Query("select new com.board_of_ads.models.dto.CategoryDtoMenu (c.id, c.name) from Category c where c.category is NULL")
    List<CategoryDtoMenu> findAllParentCategories();

    @Query("select new com.board_of_ads.models.dto.CategoryDtoMenu (c.id, c.name, c.frontName) from Category c where c.category.id = :id ")
    List<CategoryDtoMenu> findAllChildCategoriesByParentId(@Param("id") Long id);

}
