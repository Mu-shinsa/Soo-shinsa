package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId")
    Page<Product> findAllProductByBrandId(@Param("brandId") Long brandId, Pageable pageable);
    Page<Product> findAllByBrand(Pageable pageable);

    default Product findByIdOrElseThrow(Long productId) {
        return findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다.")
        );
    }
}
