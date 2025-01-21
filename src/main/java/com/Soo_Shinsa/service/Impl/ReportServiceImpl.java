package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.ReportStatus;
import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.dto.ReportProcessDto;
import com.Soo_Shinsa.dto.ReportRequestDto;
import com.Soo_Shinsa.dto.ReportResponseDto;
import com.Soo_Shinsa.entity.Report;
import com.Soo_Shinsa.model.User;
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
    public ReportResponseDto createReport(ReportRequestDto requestDto, User user) {
        Report report = reportRepository.save(requestDto.toEntity(user));
        return ReportResponseDto.toDto(report);
    }

    /**
     * 신고 처리
     * @param reportId
     * @param processDto
     */
    @Transactional
    @Override
    public void processReport(Long reportId, ReportProcessDto processDto, User user) {
        // 신고 엔티티 조회
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다. id=" + reportId));

        // 사용자 권한 검증
        validateUser(user);

        // 신고 상태 처리
        report.process(processDto.getStatus());

        // 반려 상태라면 반려 사유 추가
        if (processDto.getStatus() == ReportStatus.REJECTED) {
            report.addRejectReason(processDto.getRejectReason());
        } else {
            report.addRejectReason(null); // 반려 사유 초기화
        }

        // 변경 사항 저장
        reportRepository.save(report);
    }

    /**
     * 신고 조회
     * @param reportId
     * @return ReportResponseDto.toDto(report)
     */
    @Transactional(readOnly = true)
    @Override
    public ReportResponseDto getReport(Long reportId, User user) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다. id=" + reportId));

        // 사용자 권한 검증
        if (!user.getRole().equals(Role.ADMIN) && !report.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("신고 조회 권한이 없습니다.");
        }

        return ReportResponseDto.toDto(report);
    }

    /**
     * 신고 삭제
     * @param reportId
     */
    @Transactional
    @Override
    public void deleteReport(Long reportId, User user) {
        if (!reportRepository.existsById(reportId)) {
            throw new IllegalArgumentException("해당 신고가 존재하지 않습니다." + reportId);
        }

        validateUser(user);

        reportRepository.deleteById(reportId);
    }

    private static void validateUser(User user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new SecurityException("신고를 삭제할 권한이 없습니다.");
        }
    }
}
