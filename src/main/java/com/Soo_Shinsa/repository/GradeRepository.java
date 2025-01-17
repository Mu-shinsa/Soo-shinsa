package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.model.Grade;
import com.Soo_Shinsa.model.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByName(String name);

}
