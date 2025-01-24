package com.Soo_Shinsa.utils.user.model;

import com.Soo_Shinsa.constant.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Grade extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.Soo_Shinsa.constant.Grade name;

    @Column(nullable = false)
    private BigDecimal pointRate;

    @Column(nullable = false)
    private BigDecimal requirement;



}
