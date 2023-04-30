package ru.practicum.ewm.controller.pub.category.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.category.CategoryService;
import ru.practicum.ewm.controller.pub.category.dto.CategoryDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminCategoryController {

    CategoryService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return service.addCategory(categoryDto);
    }


    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteCategory(@PathVariable long catId) {
        service.deleteCategory(catId);

    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable long catId, @Validated({Create.class}) @RequestBody CategoryDto categoryDto) {
        return service.patchCategory(catId, categoryDto);
    }


}
