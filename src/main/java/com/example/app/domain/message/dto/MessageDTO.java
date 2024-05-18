package com.example.app.domain.message.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class MessageDTO {
    private List<String> token;
    private String message;
}
