package com.example.app.domain.alarm.dto;

import com.example.app.data.enums.Severity;
import lombok.Data;

@Data
public class AlarmDTO {
    private String target;
    private Severity severity;
}
