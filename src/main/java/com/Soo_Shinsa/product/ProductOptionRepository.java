package com.Soo_Shinsa.product;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.product.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_PRODUCT_OPTION;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>, ProductOptionCustomRepository {
    List<ProductOption> findProductOptionByProductId(Long productId);

    void deleteAllByProductId(Long productId);


    default ProductOption findByIdOrElseThrow(Long productOptionId) {
        return findById(productOptionId).orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT_OPTION));
    }
}