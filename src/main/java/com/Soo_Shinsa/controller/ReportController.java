package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.ReportProcessDto;
import com.Soo_Shinsa.dto.ReportRequestDto;
import com.Soo_Shinsa.dto.ReportResponseDto;
import com.Soo_Shinsa.service.ReportService;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDto> createReport(@Valid ReportRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImp userDetails) {
        UserUtils.getUser(userDetails);
        ReportResponseDto report = reportService.createReport(requestDto);
        return ResponseEntity.ok(report);
    }

    @PatchMapping("/{reportId}/status")
    public ResponseEntity<Void> reportProcess (@PathVariable Long reportId,
                                               @Valid @RequestBody ReportProcessDto processDto,
                                               @AuthenticationPrincipal UserDetailsImp userDetails) {
        UserUtils.getUser(userDetails);
        reportService.processReport(reportId, processDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDto> getReport(@PathVariable Long reportId,
                                                       @AuthenticationPrincipal UserDetailsImp userDetails) {
        UserUtils.getUser(userDetails);
        ReportResponseDto report = reportService.getReport(reportId);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId,
                                             @AuthenticationPrincipal UserDetailsImp userDetails) {
        UserUtils.getUser(userDetails);
        reportService.deleteReport(reportId);
        return ResponseEntity.ok().build();
    }
}
