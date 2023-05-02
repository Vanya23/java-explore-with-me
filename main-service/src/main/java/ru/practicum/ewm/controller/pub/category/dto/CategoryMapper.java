package ru.practicum.ewm.controller.pub.category.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.category.model.Category;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public CategoryDto fromCategoryToCategoryDto(Category category) {

        return new CategoryDto(category.getId(), category.getName());
    }

    public List<CategoryDto> fromCategoryListToCategoryDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category :
                categories) {
            categoryDtos.add(fromCategoryToCategoryDto(category));
        }
        return categoryDtos;
    }

    public Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }


}
