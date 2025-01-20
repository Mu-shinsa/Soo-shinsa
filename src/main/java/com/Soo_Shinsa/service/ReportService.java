package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.ReportProcessDto;
import com.Soo_Shinsa.dto.ReportRequestDto;
import com.Soo_Shinsa.dto.ReportResponseDto;

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto requestDto);
    void processReport(Long reportId, ReportProcessDto processDto);
    ReportResponseDto getReport(Long reportId);
    void deleteReport(Long reportId);
}
