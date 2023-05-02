package ru.practicum.ewm.controller.pub.category.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.category.CategoryService;
import ru.practicum.ewm.controller.pub.category.dto.CategoryDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PublicCategoryController {

    CategoryService service;


    @GetMapping
    public List<CategoryDto> getPublicCategories(@RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size
    ) {

        return service.getPublicCategories(from, size);
    }


    @GetMapping("/{catId}")
    public CategoryDto getPublicCategoriesById(@PathVariable long catId) {
        return service.getPublicCategoriesById(catId);

    }


}
