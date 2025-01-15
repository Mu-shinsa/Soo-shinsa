package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
