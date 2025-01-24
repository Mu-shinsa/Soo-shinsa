package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    @Query("SELECT po FROM ProductOption po WHERE " +
            "(:size IS NULL OR po.size = :size) AND " +
            "(:color IS NULL OR po.color = :color)")
    Page<ProductOption> findProductsByOptionalSizeAndColor(@Param("size") String size, @Param("color") String color, Pageable pageable);

    List<ProductOption> findAllByProductId(Long productId);

    void deleteAllByProductId(Long productId);
}