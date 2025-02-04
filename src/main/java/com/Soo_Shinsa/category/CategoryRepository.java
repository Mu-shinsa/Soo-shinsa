package com.Soo_Shinsa.category;

import com.Soo_Shinsa.category.model.Category;
import com.Soo_Shinsa.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_CATEGORY;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    Page<Category> findAll(Pageable pageable);

    List<Category> findByBrandId(Long brandId);

    default Category findByIdOrElseThrow(Long categoryId) {
        return findById(categoryId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_CATEGORY)
        );
    }
}