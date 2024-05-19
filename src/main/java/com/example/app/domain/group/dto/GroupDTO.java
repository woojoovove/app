package com.example.app.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GroupDTO {
    @Setter @Getter
    public static class Get {
        private Long id;
        private String name;
    }

    @Setter @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private String name;
    }

}
