package com.Soo_Shinsa.cartitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Page<CartItem> findAllByUserUserId(Long userid, Pageable pageable);

    //Order에서 Paging 처리 안되있어서 오류 발생
    Page<CartItem> findByUserUserId(Long userId, Pageable pageable);
}
