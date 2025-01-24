package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId")
    Page<Product> findAllProductByBrandId(@Param("brandId") Long brandId, Pageable pageable);
    Page<Product> findAllByBrand(Pageable pageable);

}
