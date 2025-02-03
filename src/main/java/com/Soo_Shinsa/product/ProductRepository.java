package com.Soo_Shinsa.product;

import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.Soo_Shinsa.exception.ErrorCode.NOT_FOUND_PRODUCT;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId ORDER BY p.price DESC")
    Page<Product> findAllByBrand(@Param("brandId") Long brandId, Pageable pageable);

    default Product findByIdOrElseThrow(Long productId) {
        return findById(productId).orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT));
    }

}
