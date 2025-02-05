package com.Soo_Shinsa.category;

import com.Soo_Shinsa.category.model.Category;
import com.Soo_Shinsa.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_CATEGORY;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository {

    default Category findByIdOrElseThrow(Long categoryId) {
        return findById(categoryId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_CATEGORY)
        );
    }
}