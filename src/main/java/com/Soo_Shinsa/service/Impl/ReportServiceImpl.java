package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.dto.dto.ReportProcessDto;
import com.Soo_Shinsa.dto.dto.ReportRequestDto;
import com.Soo_Shinsa.dto.dto.ReportResponseDto;
import com.Soo_Shinsa.entity.Report;
import com.Soo_Shinsa.repository.ReportRepository;
import com.Soo_Shinsa.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    @Override
    public ReportResponseDto createReport(ReportRequestDto requestDto) {
        Report report = reportRepository.save(requestDto.toEntity());
        return ReportResponseDto.toDto(report);
    }

    @Transactional
    @Override
    public void processReport(Long reportId, ReportProcessDto processDto) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다. id=" + reportId));

        report.process(processDto.getStatus());

        if ("REJECTED".equals(processDto.getStatus())) {
            report.addRejectReason(processDto.getRejectReason());
        } else {
            report.addRejectReason(null); // 반려 사유 초기화
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ReportResponseDto getReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다. id=" + reportId));

        return ReportResponseDto.toDto(report);
    }

    @Transactional
    @Override
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new IllegalArgumentException("해당 신고가 존재하지 않습니다." + reportId);
        }

        reportRepository.deleteById(reportId);
    }
}
