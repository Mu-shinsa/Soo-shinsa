package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId ORDER BY p.id DESC")
    Page<Product> findAllProductByBrandId(@Param("brandId") Long brandId, Pageable pageable);

    Page<Product> findAllByBrand(Pageable pageable);

    default Product findById(Long productId, String exceptionMessage) {
        return findById(productId).orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }

}
