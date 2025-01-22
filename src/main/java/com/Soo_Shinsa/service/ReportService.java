package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.report.ReportProcessDto;
import com.Soo_Shinsa.dto.report.ReportRequestDto;
import com.Soo_Shinsa.dto.report.ReportResponseDto;
import com.Soo_Shinsa.model.User;

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto requestDto, User user);
    void processReport(Long reportId, ReportProcessDto processDto, User user);
    ReportResponseDto getReport(Long reportId, User user);
    void deleteReport(Long reportId, User user);
}
