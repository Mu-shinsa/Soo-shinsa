package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.model.ProductOption;
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

    List<ProductOption> findProductOptionByProductId(Long productId);

    void deleteAllByProductId(Long productId);

    default ProductOption findById(Long productOptionId, String exceptionMessage) {
        return findById(productOptionId).orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }
}