package com.Soo_Shinsa.product;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.product.model.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_PRODUCT_OPTION;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    @Query("SELECT po FROM ProductOption po WHERE " +
            "(:size IS NULL OR po.size = :size) AND " +
            "(:color IS NULL OR po.color = :color)")
    Page<ProductOption> findProductsByOptionalSizeAndColor(@Param("size") String size, @Param("color") String color, Pageable pageable);

    List<ProductOption> findProductOptionByProductId(Long productId);

    void deleteAllByProductId(Long productId);


    default ProductOption findByIdOrElseThrow(Long productOptionId) {
        return findById(productOptionId).orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT_OPTION));
    }
}