package com.Soo_Shinsa.utils.user;

import com.Soo_Shinsa.utils.user.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByName(String name);

}
