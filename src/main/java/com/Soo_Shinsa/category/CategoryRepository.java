package com.Soo_Shinsa.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentId(Long parentId);

    List<Category> findByName(String name);

    default Category findByIdOrElseThrow(Long categoryId) {
        return findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다.")
        );
    }
}