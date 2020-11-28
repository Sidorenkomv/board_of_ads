package com.board_of_ads.service.impl;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.dto.CategoryDto;
import com.board_of_ads.models.dto.CategoryDtoMenu;
import com.board_of_ads.models.dto.analytics.ReportCategoryPostingDto;
import com.board_of_ads.repository.CategoryRepository;
import com.board_of_ads.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Category getCategoryById(Long catId) {
        return categoryRepository.findCategoryById(catId);
    }

    private void collectChild(Category categoryWithChildren, Set<CategoryDto> collect) {
        categoryRepository.findCategoriesByCategory(categoryWithChildren.getId())
                .stream()
                .filter(Category::isActive)
                .sorted(Comparator.comparing(Category::getId))
                .forEach(cat -> {
                    collect.add(new CategoryDto(cat.getId(), cat.getName(), cat.getCategory().getName(), cat.getLayer()));
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

    @Override
    public List<CategoryDto> findAllParentCategoriesById(Long id) {
        return categoryRepository.findAllParentCategoriesById(id);
    }

    private List<Category> findParentByName(String name) {
        return categoryRepository.findParentLikeName("%" + name);
    }


    public Optional<Category> getCategoryByFrontName(String frontName) {
        return Optional.ofNullable(categoryRepository.findCategoryByFrontName(frontName));
    }

    @Override
    public List<ReportCategoryPostingDto> getNumberOfPostings(String date) {
        List<LocalDateTime> localDateTimes = dateConvertation(date);
        List<ReportCategoryPostingDto> childList = categoryRepository.findAllByDatePostingBetween(localDateTimes.get(0), localDateTimes.get(1));
        List<ReportCategoryPostingDto> parentList = new ArrayList<>();

        for (ReportCategoryPostingDto child : childList) {
            parentList.add(new ReportCategoryPostingDto(child.getParentCategory().getLocalNumber(), child.getParentCategory().getName().toUpperCase(), child.getParentCategory().getLayer(), child.getPostsCount(), child.getActivePostsCount(), child.getArchivePostsCount()));
        }

        for (int i = 0; i < parentList.size(); i++) {
            int k = i + 1;
            while (k < parentList.size()) {
                if (parentList.get(i).getCategory().equals(parentList.get(k).getCategory())) {
                    parentList.set(i, new ReportCategoryPostingDto(parentList.get(i).getLocalNumber(), parentList.get(i).getCategory(), parentList.get(i).getLayer(), parentList.get(i).getPostsCount() + parentList.get(k).getPostsCount(),
                            parentList.get(i).getActivePostsCount() + parentList.get(k).getActivePostsCount(),
                            parentList.get(i).getArchivePostsCount() + parentList.get(k).getArchivePostsCount()));
                    parentList.remove(k);
                    k--;
                }
                k++;
            }
        }

        List<ReportCategoryPostingDto> grandParentList = new ArrayList<>();

        for (ReportCategoryPostingDto child : childList) {
            if (child.getLayer() == 3) {
                grandParentList.add(new ReportCategoryPostingDto(child.getParentCategory().getCategory().getLocalNumber(), child.getParentCategory().getCategory().getName().toUpperCase(), child.getParentCategory().getCategory().getLayer(), child.getPostsCount(), child.getActivePostsCount(), child.getArchivePostsCount()));
            }
        }

        for (int i = 0; i < grandParentList.size(); i++) {
            int k = i + 1;
            while (k < grandParentList.size()) {
                if (grandParentList.get(i).getCategory().equals(grandParentList.get(k).getCategory())) {
                    grandParentList.set(i, new ReportCategoryPostingDto(grandParentList.get(i).getLocalNumber(), grandParentList.get(i).getCategory(), grandParentList.get(i).getLayer(), grandParentList.get(i).getPostsCount() + grandParentList.get(k).getPostsCount(),
                            grandParentList.get(i).getActivePostsCount() + grandParentList.get(k).getActivePostsCount(),
                            grandParentList.get(i).getArchivePostsCount() + grandParentList.get(k).getArchivePostsCount()));
                    grandParentList.remove(k);
                    k--;
                }
                k++;
            }
        }

        List<ReportCategoryPostingDto> grandGrandParentList = new ArrayList<>();
        for (ReportCategoryPostingDto child : childList) {
            if (child.getLayer() == 4) {
                grandGrandParentList.add(new ReportCategoryPostingDto(child.getParentCategory().getCategory().getCategory().getLocalNumber(), child.getParentCategory().getCategory().getCategory().getName().toUpperCase(), child.getParentCategory().getCategory().getCategory().getLayer(), child.getPostsCount(), child.getActivePostsCount(), child.getArchivePostsCount()));
            }
        }

        for (int i = 0; i < grandGrandParentList.size(); i++) {
            int k = i + 1;
            while (k < grandGrandParentList.size()) {
                if (grandGrandParentList.get(i).getCategory().equals(grandGrandParentList.get(k).getCategory())) {
                    grandGrandParentList.set(i, new ReportCategoryPostingDto(grandGrandParentList.get(i).getLocalNumber(), grandGrandParentList.get(i).getCategory(), grandGrandParentList.get(i).getLayer(), grandGrandParentList.get(i).getPostsCount() + grandGrandParentList.get(k).getPostsCount(),
                            grandGrandParentList.get(i).getActivePostsCount() + grandGrandParentList.get(k).getActivePostsCount(),
                            grandGrandParentList.get(i).getArchivePostsCount() + grandGrandParentList.get(k).getArchivePostsCount()));
                    grandGrandParentList.remove(k);
                    k--;
                }
                k++;
            }
        }

        List<ReportCategoryPostingDto> combinedList = new ArrayList<>();
        combinedList.addAll(childList);
        combinedList.addAll(parentList);
        combinedList.addAll(grandParentList);
        combinedList.addAll(grandGrandParentList);

        Collections.sort(combinedList, ReportCategoryPostingDto.COMPARE_BY_localNumber);
        return combinedList;
    }

    private List<LocalDateTime> dateConvertation(String date) {

        String[] arr = date.split("\\D+");

        List<Integer> dateValues = Arrays.stream(arr)
                .filter(a -> !a.equals(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        LocalDateTime startDateTime = LocalDateTime.of(dateValues.get(2), dateValues.get(1), dateValues.get(0), 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(dateValues.get(5), dateValues.get(4), dateValues.get(3), 23, 59);

        List<LocalDateTime> localDateTimeList = new ArrayList<>();
        localDateTimeList.add(startDateTime);
        localDateTimeList.add(endDateTime);

        return localDateTimeList;
    }

}