package com.example.app.domain.service.dto;

import lombok.Setter;

public class GroupDTO {
    @Setter
    public static class Get {
        private Long id;
        private String name;
    }

    @Setter
    public static class Create {
        private String name;
    }

}
