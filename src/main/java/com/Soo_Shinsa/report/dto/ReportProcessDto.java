package com.Soo_Shinsa.report.dto;

import com.Soo_Shinsa.constant.ReportStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportProcessDto {

    @NotNull(message = "신고 값은 필수입니다.")
    private ReportStatus status;

    @NotEmpty(message = "거절 사유는 필수입니다.")
    private String rejectReason;

    public ReportProcessDto(ReportStatus status, String rejectReason) {
        this.status = status;
        this.rejectReason = rejectReason;
    }
}
