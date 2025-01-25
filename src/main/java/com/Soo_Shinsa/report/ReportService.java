package com.Soo_Shinsa.report;

import com.Soo_Shinsa.report.dto.ReportProcessDto;
import com.Soo_Shinsa.report.dto.ReportRequestDto;
import com.Soo_Shinsa.report.dto.ReportResponseDto;
<<<<<<< HEAD
import com.Soo_Shinsa.utils.user.model.User;

=======
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;
>>>>>>> 4b39b3825ec2c4739765ba1c6974be187a12dc07

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto requestDto, User user);
    void processReport(Long reportId, ReportProcessDto processDto, User user);
    ReportResponseDto getReport(Long reportId, User user);
    void deleteReport(Long reportId, User user);
    Page<ReportResponseDto> getAllReports(User user, int page, int size);
}
