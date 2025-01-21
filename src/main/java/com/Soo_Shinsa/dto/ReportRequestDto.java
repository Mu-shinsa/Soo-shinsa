package com.Soo_Shinsa.dto;

import com.Soo_Shinsa.constant.ReportStatus;
import com.Soo_Shinsa.constant.TargetType;
import com.Soo_Shinsa.entity.Report;
import com.Soo_Shinsa.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequestDto {

    @NotNull (message = "신고 대상은 필수값 입니다.")
    private Long targetId;

    @NotNull (message = "신고 대상 타입은 필수값 입니다.")
    private TargetType targetType;

    @NotNull (message = "신고 상태는 필수값 입니다.")
    private ReportStatus status;

    private String content;

    public Report toEntity(User user) {
        return Report.builder()
                .targetId(targetId)
                .targetType(targetType)
                .status(ReportStatus.OPEN)
                .content(content)
                .user(user)
                .build();
    }
}
