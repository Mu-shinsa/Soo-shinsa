package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.Brand;
import com.Soo_Shinsa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    List<Product> findAllByBrandId(Brand brandId);

    default Product findByIdOrElseThrow(Long productId) {
        return findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다.")
        );
    }
}
