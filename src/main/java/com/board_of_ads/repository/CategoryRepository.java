package com.board_of_ads.repository;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDtoMenu;
import com.board_of_ads.models.dto.analytics.ReportCategoryPostingDto;
import com.board_of_ads.models.dto.analytics.ReportCityPostingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    Category findCategoryByFrontName(String frontName);

    @Query("select new com.board_of_ads.models.dto.analytics.ReportCategoryPostingDto(" +
            "c.name, count (c.name), sum (case when p.isActive = true then 1 else 0 end), sum (case when p.isActive = true then 0 else 1 end)" +
            ")" +
            " from Category c, Posting p where p.category = c AND p.datePosting BETWEEN :startDate and :endDate GROUP BY c.name")
    List<ReportCategoryPostingDto> findAllByDatePostingBetween(LocalDateTime startDate, LocalDateTime endDate);

}
