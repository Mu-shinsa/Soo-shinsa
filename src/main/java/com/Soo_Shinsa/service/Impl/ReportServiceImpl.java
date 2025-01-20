package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.dto.ReportProcessDto;
import com.Soo_Shinsa.dto.ReportRequestDto;
import com.Soo_Shinsa.dto.ReportResponseDto;
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

    /**
     * 신고 생성
     * @param requestDto
     * @return ReportResponseDto.toDto(report)
     */
    @Transactional
    @Override
    public ReportResponseDto createReport(ReportRequestDto requestDto) {
        Report report = reportRepository.save(requestDto.toEntity());
        return ReportResponseDto.toDto(report);
    }

    /**
     * 신고 처리
     * @param reportId
     * @param processDto
     */
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

    /**
     * 신고 조회
     * @param reportId
     * @return ReportResponseDto.toDto(report)
     */
    @Transactional(readOnly = true)
    @Override
    public ReportResponseDto getReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다. id=" + reportId));

        return ReportResponseDto.toDto(report);
    }

    /**
     * 신고 삭제
     * @param reportId
     */
    @Transactional
    @Override
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new IllegalArgumentException("해당 신고가 존재하지 않습니다." + reportId);
        }

        reportRepository.deleteById(reportId);
    }
}
