package com.Soo_Shinsa.report.dto;

import com.Soo_Shinsa.constant.ReportStatus;
import com.Soo_Shinsa.constant.TargetType;
<<<<<<< HEAD
import com.Soo_Shinsa.report.Report;
import com.Soo_Shinsa.utils.user.model.User;
=======
import com.Soo_Shinsa.report.model.Report;
import com.Soo_Shinsa.user.model.User;
>>>>>>> 4b39b3825ec2c4739765ba1c6974be187a12dc07
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
