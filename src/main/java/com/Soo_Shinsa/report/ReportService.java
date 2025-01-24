package com.Soo_Shinsa.report;

import com.Soo_Shinsa.report.report.ReportProcessDto;
import com.Soo_Shinsa.report.report.ReportRequestDto;
import com.Soo_Shinsa.report.report.ReportResponseDto;
import com.Soo_Shinsa.utils.user.model.User;

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto requestDto, User user);
    void processReport(Long reportId, ReportProcessDto processDto, User user);
    ReportResponseDto getReport(Long reportId, User user);
    void deleteReport(Long reportId, User user);
}
