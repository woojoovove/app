package com.example.app.domain.alarm.controller;

import com.example.app.domain.alarm.dto.AlarmDTO;
import com.example.app.domain.alarm.service.AlarmService;
import com.example.app.global.response.DataResponse;
import com.example.app.global.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alarm")
@Tag(name = "알림 기능")
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping("/")
    @Operation(summary = "알림 푸시", description = "원하는 사용자나 그룹에 알림을 보내는 API",
        responses = {
            @ApiResponse(responseCode = "200", description = "알림 푸시 성공"),
            @ApiResponse(responseCode = "500", description = "알림 푸시 실패",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    public ResponseEntity<DataResponse<Long>> pushAlarm(@RequestBody @Valid AlarmDTO alarmDTO) {
        return DataResponse.success(alarmService.pushAlarm(alarmDTO));
    }
}
