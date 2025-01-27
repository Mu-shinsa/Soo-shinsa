package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByBrand(Long brandId,Pageable pageable);

    default Product findById(Long productId, String exceptionMessage) {
        return findById(productId).orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }

}
