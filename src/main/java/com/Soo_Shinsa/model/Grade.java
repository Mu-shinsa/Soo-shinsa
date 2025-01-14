package com.Soo_Shinsa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Grade extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal pointRate;

    @Column(nullable = false)
    private BigDecimal requirement;



}
