package com.Soo_Shinsa.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllCategory(Pageable pageable);

    List<Category> findByBrandId(Long brandId);

    default Category findByIdOrElseThrow(Long categoryId) {
        return findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다.")
        );
    }
}