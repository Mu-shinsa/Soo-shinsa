package com.Soo_Shinsa.report.report;

import com.Soo_Shinsa.constant.ReportStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportProcessDto {

    @NotBlank(message = "신고 값은 필수입니다.")
    private ReportStatus status;

    private String rejectReason;

    public ReportProcessDto(ReportStatus status, String rejectReason) {
        this.status = status;
        this.rejectReason = rejectReason;
    }
}
