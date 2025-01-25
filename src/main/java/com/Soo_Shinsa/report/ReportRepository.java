package com.Soo_Shinsa.report;

import com.Soo_Shinsa.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
