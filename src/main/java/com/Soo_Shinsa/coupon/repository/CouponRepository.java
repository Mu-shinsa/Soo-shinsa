package com.Soo_Shinsa.coupon.repository;

import com.Soo_Shinsa.coupon.model.Coupon;
import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.NotFoundException;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon findByIdOrElseThrow(Long couponId) {
        return findById(couponId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_COUPON_COUNT)
        );
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :id")
    Optional<Coupon> findByIdForUpdate(@Param("id") Long couponId);
}
