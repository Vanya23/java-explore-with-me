package ru.practicum.ewm.controller.pub.category;


import ru.practicum.ewm.controller.pub.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);

    CategoryDto patchCategory(long catId, CategoryDto categoryDto);

    List<CategoryDto> getPublicCategories(int from, int size);

    CategoryDto getPublicCategoriesById(long catId);
}
