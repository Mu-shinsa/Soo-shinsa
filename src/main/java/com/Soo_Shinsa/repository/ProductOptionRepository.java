package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findById(Long id);

    List<ProductOption> findAllByProductId(Product productId);
}
