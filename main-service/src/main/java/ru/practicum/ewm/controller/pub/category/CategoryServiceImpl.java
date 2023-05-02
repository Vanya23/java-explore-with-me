package ru.practicum.ewm.controller.pub.category;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.controller.pub.GeneratePageableObj;
import ru.practicum.ewm.controller.pub.category.dto.CategoryDto;
import ru.practicum.ewm.controller.pub.category.dto.CategoryMapper;
import ru.practicum.ewm.controller.pub.category.model.Category;
import ru.practicum.ewm.controller.pub.error.exception.BadRequestException;
import ru.practicum.ewm.controller.pub.error.exception.ConflictException;
import ru.practicum.ewm.controller.pub.error.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository repository;

    CategoryMapper categoryMapper;
    GeneratePageableObj myServicePage;

    Sort sortIdAsc = Sort.by(Sort.Direction.ASC, "id"); // по возрастанию


    @Override
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (Strings.isBlank(categoryDto.getName())) throw new BadRequestException("");

        try {
            Category category = repository.save(categoryMapper.fromCategoryDtoToCategory(categoryDto));
            return categoryMapper.fromCategoryToCategoryDto(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("" + e.getClass());
        }

    }


    @Override
    @Transactional
    public void deleteCategory(long catId) {
        try {
            repository.deleteById(catId);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("" + e.getClass());
        }
    }

    @Override
    @Transactional
    public CategoryDto patchCategory(long catId, CategoryDto categoryDto) {
        if (!repository.existsById(catId)) throw new NotFoundException(""); //Категория не найдена или недоступна
        if (Strings.isBlank(categoryDto.getName())) throw new BadRequestException("");
        categoryDto.setId(catId);
        Category catForPatch = repository.getReferenceById(categoryDto.getId());
        if (Strings.isNotBlank(categoryDto.getName())) {
            catForPatch.setName(categoryDto.getName());
        }


        try {
            repository.saveAndFlush(catForPatch);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getClass() + " ");
        }


        return categoryMapper.fromCategoryToCategoryDto(catForPatch);
    }

    @Override
    public List<CategoryDto> getPublicCategories(int from, int size) {

        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, sortIdAsc);

        Page<Category> page = repository.findAll(pageable);

        List<Category> categories = page.getContent();


        return categoryMapper.fromCategoryListToCategoryDtoList(categories);
    }

    @Override
    public CategoryDto getPublicCategoriesById(long catId) {
        if (!repository.existsById(catId)) throw new NotFoundException("");
        return categoryMapper.fromCategoryToCategoryDto(repository.getReferenceById(catId));
    }


}
