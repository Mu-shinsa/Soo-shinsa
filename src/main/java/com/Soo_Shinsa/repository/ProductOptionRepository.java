package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findById(Long id);

    List<ProductOption> findAllByProductId(Product productId);

    default ProductOption findByIdOrElseThrow(Long optionId) {
        return findById(optionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 옵션을 찾을 수 없습니다.")
        );
    }
}
