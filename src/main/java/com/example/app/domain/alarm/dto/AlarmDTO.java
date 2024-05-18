package com.example.app.domain.alarm.dto;

import com.example.app.data.enums.Severity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * {
 *   "target": [ "@user1", "@@team1", "@all" ]
 *   "severity": "normal",
 *   "message" : "node1 down"
 * }
 */
@Data @Builder
public class AlarmDTO {
    @NotBlank
    @Schema(description = "알림 대상 목록", example = "[\"@user1\", \"@@group1\"]")
    private List<String> target;
    @NotBlank
    @Schema(description = "심각도", defaultValue = "MEDIUM", allowableValues = {"CRITICAL", "HIGH", "MEDIUM", "LOW"})
    private Severity severity;
    @Schema(description = "메시지 내용", example = "node 1 down")
    private String message;
}
