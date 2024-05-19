package com.example.app.domain.membership.dto;

import com.example.app.global.swagger.SchemaDescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class MembershipDTO {
    @Builder
    @Getter
    @Schema(description = "그룹 참가 Input DTO")
    public static class JoinDTO {

        @NotBlank
        @Schema(description = SchemaDescriptionUtils.User.name, example = "user1")
        private String userNickname;

        @NotBlank
        @Schema(description = SchemaDescriptionUtils.Group.name, example = "group1")
        private String groupName;
    }
    @Getter @Setter @Builder
    @Schema(description = "그룹 참가 결과 DTO")
    public static class JoinResultDTO {

        @Schema(description = SchemaDescriptionUtils.Membership.id, example = "1")
        private Long membershipId;
        @Schema(description = SchemaDescriptionUtils.User.id, example = "1")
        private Long userId;
        @Schema(description = SchemaDescriptionUtils.Group.id, example = "1")
        private Long groupId;
        @Schema(description = SchemaDescriptionUtils.User.name, example = "user1")
        private String userNickname;
        @Schema(description = SchemaDescriptionUtils.Group.name, example = "group1")
        private String groupName;
    }
    @Getter @Builder
    public static class LeaveDTO {
        @NotBlank
        @Schema(description = SchemaDescriptionUtils.User.name, example = "user11")
        private String userNickname;
        @NotBlank
        @Schema(description = SchemaDescriptionUtils.Group.name, example = "group1")
        private String groupName;
    }

    @Getter @Setter @Builder
    public static class LeaveResultDTO {
        @Schema(description = SchemaDescriptionUtils.Membership.id, example = "1")
        private Long membershipId;
        @Schema(description = SchemaDescriptionUtils.User.id, example = "1")
        private Long userId;
        @Schema(description = SchemaDescriptionUtils.Group.id, example = "1")
        private Long groupId;
        @Schema(description = SchemaDescriptionUtils.User.name, example = "user1")
        private String userNickname;
        @Schema(description = SchemaDescriptionUtils.Group.name, example = "group1")
        private String groupName;
    }
}
