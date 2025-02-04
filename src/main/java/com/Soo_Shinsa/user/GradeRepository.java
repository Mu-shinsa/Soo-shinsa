package com.Soo_Shinsa.user;

import com.Soo_Shinsa.constant.GradeType;
import com.Soo_Shinsa.user.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByName(GradeType name);


}
