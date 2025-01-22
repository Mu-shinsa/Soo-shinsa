package com.Soo_Shinsa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsId;

    //제품 관련 정보
    private String productName;
    private BigDecimal price;
    private String categoryName;
    private String brandName;

    //주문 관련 정보
    private LocalDateTime orderDate;
    private String orderStatus;

    //구매자 관련 정보



}
