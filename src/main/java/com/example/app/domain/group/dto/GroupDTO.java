package com.example.app.domain.group.dto;

import lombok.Getter;
import lombok.Setter;

public class GroupDTO {
    @Setter
    public static class Get {
        private Long id;
        private String name;
    }

    @Setter @Getter
    public static class Create {
        private String name;
    }

}
