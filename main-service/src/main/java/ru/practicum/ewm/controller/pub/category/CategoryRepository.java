package ru.practicum.ewm.controller.pub.category;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.controller.pub.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAll(Pageable pageable);

    Boolean existsByName(String name);

    Category findByName(String name);

}