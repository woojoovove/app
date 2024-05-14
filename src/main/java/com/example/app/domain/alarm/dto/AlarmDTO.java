package com.example.app.domain.alarm.dto;

import com.example.app.data.enums.Severity;
import java.util.List;
import lombok.Data;

/**
 * {
 *   "target": [ "@user1", "@@team1", "@all" ]
 *   "severity": "normal",
 *   "message" : "node1 down"
 * }
 */
@Data
public class AlarmDTO {
    private List<String> target;
    private Severity severity;
    private String message;
}
