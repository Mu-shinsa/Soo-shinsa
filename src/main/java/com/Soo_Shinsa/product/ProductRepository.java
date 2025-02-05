package com.Soo_Shinsa.product;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_PRODUCT;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {

    default Product findByIdOrElseThrow(Long productId) {
        return findById(productId).orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT));
    }

}
