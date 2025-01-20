package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.ReportProcessDto;
import com.Soo_Shinsa.dto.ReportRequestDto;
import com.Soo_Shinsa.dto.ReportResponseDto;
import com.Soo_Shinsa.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDto> createReport(@Valid ReportRequestDto requestDto) {
        ReportResponseDto report = reportService.createReport(requestDto);
        return ResponseEntity.ok(report);
    }

    @PatchMapping("/{reportId}/status")
    public ResponseEntity<Void> reportProcess (@PathVariable Long reportId,
                                                           @Valid @RequestBody ReportProcessDto processDto) {
        reportService.processReport(reportId, processDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDto> getReport(@PathVariable Long reportId) {
        ReportResponseDto report = reportService.getReport(reportId);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.ok().build();
    }
}
