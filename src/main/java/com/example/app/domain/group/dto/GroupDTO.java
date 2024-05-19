package com.example.app.domain.group.dto;

import com.example.app.global.swagger.SchemaDescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GroupDTO {
    @Setter @Getter
    public static class Get {
        @Schema(description = SchemaDescriptionUtils.Group.id, example = "1")
        private Long id;
        @Schema(description = SchemaDescriptionUtils.Group.name, example = "group1")
        private String name;
    }

    @Setter @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        @Schema(description = SchemaDescriptionUtils.Group.name, example = "group11")
        private String name;
    }

}
