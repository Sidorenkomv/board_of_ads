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
        List<ReportCategoryPostingDto> grandParentList = new ArrayList<>();
        List<ReportCategoryPostingDto> grandGrandParentList = new ArrayList<>();

        for (ReportCategoryPostingDto child : childList) {
            parentList.add(new ReportCategoryPostingDto(child.getParentCategory().getName(), child.getParentCategory().getLayer(), child.getPostsCount(), child.getActivePostsCount(), child.getArchivePostsCount()));
            if (child.getLayer() == 3 || child.getLayer() == 4) {
                grandParentList.add(new ReportCategoryPostingDto(child.getParentCategory().getCategory().getName(), child.getParentCategory().getCategory().getLayer(), child.getPostsCount(), child.getActivePostsCount(), child.getArchivePostsCount()));
            }
            if (child.getLayer() == 4) {
                grandGrandParentList.add(new ReportCategoryPostingDto(child.getParentCategory().getCategory().getCategory().getName(), child.getParentCategory().getCategory().getCategory().getLayer(), child.getPostsCount(), child.getActivePostsCount(), child.getArchivePostsCount()));
            }
        }

        mergeParent(parentList);
        mergeParent(grandParentList);
        mergeParent(grandGrandParentList);

        Collections.sort(childList, ReportCategoryPostingDto.COMPARE_BY_LAYER);
        Collections.sort(parentList, ReportCategoryPostingDto.COMPARE_BY_LAYER);
        Collections.sort(grandParentList, ReportCategoryPostingDto.COMPARE_BY_LAYER);

        List<ReportCategoryPostingDto> mergedList = new ArrayList<>();

        for (ReportCategoryPostingDto child : childList) {
            if (child.getLayer() == 2) {
                for (int i = 0; i < parentList.size(); i++) {

                    if (child.getParentCategory().getName() == parentList.get(i).getCategory()) {
                        mergedList.add(parentList.get(i));
                        mergedList.add(child);
                        parentList.remove(i);

                    } else {
                        mergedList.add(child);
                    }
                    break;
                }

            } else if (child.getLayer() == 3 && grandParentList.size() != 0) {

                for (int i = 0; i < grandParentList.size(); i++) {
                    if (child.getParentCategory().getCategory().getName() == grandParentList.get(i).getCategory()) {
                        mergedList.add(grandParentList.get(i));
                        grandParentList.remove(i);

                        for (int j = 0; j < parentList.size(); j++) {
                            if (child.getParentCategory().getName() == parentList.get(j).getCategory()) {
                                mergedList.add(parentList.get(j));
                                mergedList.add(child);
                                parentList.remove(j);
                            } else {
                                mergedList.add(child);
                            }
                            break;
                        }
                    } else {
                        mergedList.add(child);
                    }
                    break;
                }
            } else if (child.getLayer() == 3 && grandParentList.size() == 0) {
                mergedList.add(child);

            } else if (child.getLayer() == 4 && grandGrandParentList.size() != 0) {

                for (int i = 0; i < grandGrandParentList.size(); i++) {
                    if (child.getParentCategory().getCategory().getCategory().getName() == grandGrandParentList.get(i).getCategory()) {
                        mergedList.add(grandGrandParentList.get(i));
                        grandGrandParentList.remove(i);

                        for (int j = 0; j < grandParentList.size(); j++) {
                            if (child.getParentCategory().getCategory().getName() == grandParentList.get(j).getCategory()) {
                                mergedList.add(grandParentList.get(j));
                                //            mergedList.add(child);
                                grandParentList.remove(j);

                                for (int k = 0; k < parentList.size(); k++) {
                                    if (child.getParentCategory().getName() == parentList.get(k).getCategory()) {
                                        mergedList.add(parentList.get(k));
                                        mergedList.add(child);
                                        parentList.remove(k);
                                    } else {
                                        mergedList.add(child);
                                    }
                                    break;
                                }
                            } else {
                                mergedList.add(child);
                            }
                            break;
                        }
                    } else {
                        mergedList.add(child);
                    }
                    break;
                }
            } else if (child.getLayer() == 4 && grandGrandParentList.size() == 0) {

                if (parentList.size() != 0) {

                    for (int k = 0; k < parentList.size(); k++) {
                        if (child.getParentCategory().getName() == parentList.get(k).getCategory()) {
                            mergedList.add(parentList.get(k));
                            mergedList.add(child);
                            parentList.remove(k);
                        } else {
                            mergedList.add(child);
                        }
                        break;
                    }
                } else {
                    mergedList.add(child);
                }
            }
        }

        for (int i = 0; i < mergedList.size(); i++) {
            if (mergedList.get(i).getLayer() == 1) {
                ReportCategoryPostingDto obj = mergedList.get(i);
                obj.setCategory(obj.getCategory().toUpperCase());
                mergedList.set(i, obj);
            }
        }

        return mergedList;
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

    public void mergeParent(List<ReportCategoryPostingDto> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int k = i + 1; k < list.size(); k++) {
                if (list.get(i).getCategory().equals(list.get(k).getCategory())) {
                    list.set(i, new ReportCategoryPostingDto(list.get(i).getCategory(),
                            list.get(i).getLayer(), list.get(i).getPostsCount() + list.get(k).getPostsCount(),
                            list.get(i).getActivePostsCount() + list.get(k).getActivePostsCount(),
                            list.get(i).getArchivePostsCount() + list.get(k).getArchivePostsCount()));
                    list.remove(k);
                    k--;
                }
            }
        }
    }
}