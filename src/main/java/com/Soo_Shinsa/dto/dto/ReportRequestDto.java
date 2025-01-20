package com.Soo_Shinsa.dto.dto;

import com.Soo_Shinsa.constant.ReportStatus;
import com.Soo_Shinsa.constant.TargetType;
import com.Soo_Shinsa.entity.Report;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequestDto {

    @NotNull
    private Long targetId;

    @NotNull
    private TargetType targetType;

    @NotNull
    private ReportStatus status;

    private String content;

    public Report toEntity() {
        return Report.builder()
                .targetId(targetId)
                .targetType(targetType)
                .status(ReportStatus.OPEN)
                .content(content)
                .build();
    }
}
