package com.Soo_Shinsa.report;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.report.dto.ReportProcessDto;
import com.Soo_Shinsa.report.dto.ReportRequestDto;
import com.Soo_Shinsa.report.dto.ReportResponseDto;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDto> createReport(@Valid @RequestBody ReportRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        ReportResponseDto report = reportService.createReport(requestDto, user);
        return ResponseEntity.ok(report);
    }

    @PatchMapping("/{reportId}/status")
    public ResponseEntity<Void> reportProcess (@PathVariable Long reportId,
                                               @Valid @RequestBody ReportProcessDto processDto,
                                               @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        reportService.processReport(reportId, processDto, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDto> getReport(@PathVariable Long reportId,
                                                       @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        ReportResponseDto report = reportService.getReport(reportId, user);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId,
                                             @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        reportService.deleteReport(reportId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllReports(@AuthenticationPrincipal UserDetailsImp userDetails,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        User user = UserUtils.getUser(userDetails);
        Page<ReportResponseDto> reports = reportService.getAllReports(user, page, size);
        return ResponseEntity.ok(reports);
    }
}
