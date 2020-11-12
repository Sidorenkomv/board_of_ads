package com.board_of_ads.service.impl;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;
import com.board_of_ads.models.dto.CategoryDtoMenu;
import com.board_of_ads.repository.CategoryRepository;
import com.board_of_ads.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findCategoryByName(name));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Set<CategoryDto> findAllCategory() {
        Set<CategoryDto> category = new LinkedHashSet<>();
        categoryRepository.findAll().stream()
                .filter(Category::isActive)
                .sorted(Comparator.comparing(Category::getId))
                .forEach(cat -> {
                    if (cat.getCategory() == null) {
                        category.add(new CategoryDto(cat.getId(), cat.getName(), null, cat.getLayer()));
                        collectChild(cat, category);
                    }
        });
        return category;
    }

    private void collectChild(Category categoryWithChildren, Set<CategoryDto> collect) {
        categoryRepository.findCategoriesByCategory(categoryWithChildren.getId())
                .stream()
                .filter(Category::isActive)
                .sorted(Comparator.comparing(Category::getId))
                .forEach(cat -> {
                    collect.add(new CategoryDto(cat.getId(), cat.getName(), cat.getCategory().getName(),cat.getLayer()));
                    collectChild(cat, collect);
                });
    }

    @Override
    public Optional<CategoryDto> getCategoryDtoById(Long id) {
        var category = categoryRepository.findCategoryById(id);
        var categoryDto = new CategoryDto(
                category.getId(),
                category.getName(),
                category.getCategory() == null ? null : category.getCategory().getName(),
                category.getLayer() == 0 ? 0 : category.getLayer());
        return Optional.of(categoryDto);
    }

    @Override
    public Optional<CategoryDto> getCategoryDtoByName(String name) {
        var category = categoryRepository.findCategoryByName(name);
        var categoryDto = new CategoryDto(
                category.getId(),
                category.getName(),
                category.getCategory() == null ? null : category.getCategory().getName(),
                category.getLayer() == 0 ? 0 : category.getLayer());
        return Optional.of(categoryDto);
    }

    @Override
    public Category updateCategory(String oldName, CategoryDto categoryDto) {
        log.info("Get parameters oldName is: {}, categoryDto is: {}", oldName, categoryDto);
        var children = categoryRepository.findCategoriesByCategory(categoryDto.getId());
        var categoryOld = categoryRepository.findCategoryById(categoryDto.getId());
        log.info("Get category from bd: {} by id {}", categoryOld, categoryDto.getId());
        children.forEach(child -> {
            categoryRepository.findCategoriesByCategory(child.getId()).forEach(c -> c.setName(c.getName().replace(oldName, categoryDto.getName())));
            child.setName(child.getName().replace(oldName, categoryDto.getName()));
        });
        categoryOld.setName(categoryOld.getName().replace(oldName, categoryDto.getName()));
        if ((categoryOld.getCategory() != null)
                &&
                (!categoryOld.getCategory().getName().endsWith(categoryDto.getParentName()))) {
            var cat = findParentByName(categoryDto.getParentName()).stream().findFirst().get();
            log.info("Get parent from bd: {} by name {}", cat, categoryDto.getParentName());
            return categoryRepository.save(new Category(categoryDto.getId(), cat.getName() + ":" + categoryDto.getName(), cat, cat.getLayer() + 1));
        }
        var parent = getCategoryByName(categoryDto.getParentName()).orElse(null);
        return categoryRepository.save(
                new Category(categoryDto.getId(), categoryDto.getName(), parent, parent != null ? parent.getLayer() + 1 : 1));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findCategoriesByCategory(id)
                .forEach(child -> child.setActive(false));
        categoryRepository.findCategoryById(id).setActive(false);
    }

    @Override
    public Category createCategory(CategoryDto category) {
        if (category.getParentName().equals("")) {
            return categoryRepository.save(new Category(category.getName(), null, 1));
        }
        var categoryParentFromDB = findParentByName(category.getParentName()).stream().findFirst().get();
        return categoryRepository.save(new Category(category.getName(), categoryParentFromDB, categoryParentFromDB.getLayer() + 1));
    }

    @Override
    public List<CategoryDtoMenu> allParentCategory() {
        return categoryRepository.findAllParentCategories();
    }

    @Override
    public List<CategoryDtoMenu> findChildCatById(Long id) {
        return categoryRepository.findAllChildCategoriesByParentId(id);
    }

    private List<Category> findParentByName(String name) {
        return categoryRepository.findParentLikeName("%" + name);
    }


    public Optional<Category> getCategoryByFrontName(String frontName) {
        return Optional.ofNullable(categoryRepository.findCategoryByFrontName(frontName));
    }



}