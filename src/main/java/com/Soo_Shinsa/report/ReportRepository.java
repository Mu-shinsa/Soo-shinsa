package com.Soo_Shinsa.report;

import com.Soo_Shinsa.report.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {
    default Report findById(Long reportId, String exceptionMessage) {
        return findById(reportId).orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }

    @Query("SELECT r FROM Report r ORDER BY r.createdAt DESC")
    Page<Report> findAllReports(Pageable pageable);
}
