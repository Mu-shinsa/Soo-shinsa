package com.Soo_Shinsa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class UserGrade extends BaseCreatedTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGradeId;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    public UserGrade(Grade grade) {
        this.grade = grade;
    }
}
