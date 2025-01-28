package com.Soo_Shinsa.report;

import com.Soo_Shinsa.report.dto.ReportProcessDto;
import com.Soo_Shinsa.report.dto.ReportRequestDto;
import com.Soo_Shinsa.report.dto.ReportResponseDto;

import com.Soo_Shinsa.user.model.User;


import org.springframework.data.domain.Page;


public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto requestDto, User user);
    void processReport(Long reportId, ReportProcessDto processDto, User user);
    ReportResponseDto getReport(Long reportId, User user);
    void deleteReport(Long reportId, User user);
    Page<ReportResponseDto> getAllReports(User user, int page, int size);
}
