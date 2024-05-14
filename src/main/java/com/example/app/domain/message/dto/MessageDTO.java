package com.example.app.domain.message.dto;

import java.util.List;
import lombok.Data;

@Data
public class MessageDTO {
    private List<String> token;
    private String message;
}
