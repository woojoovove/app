package com.example.app.domain.alarm.controller;

import com.example.app.domain.alarm.dto.AlarmDTO;
import com.example.app.domain.alarm.service.AlarmService;
import com.example.app.global.response.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<Long>> pushAlarm(@RequestBody AlarmDTO alarmDTO) {
        return DataResponse.success(alarmService.pushAlarm(alarmDTO));
    }
}
