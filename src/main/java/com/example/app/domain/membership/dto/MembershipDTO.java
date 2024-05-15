package com.example.app.domain.membership.dto;

import com.example.app.data.entity.GroupsEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MembershipDTO {
    @Getter
    public static class JoinDTO {
        private String userNickname;
        private String groupName;
    }
    @Getter @Setter @Builder
    public static class JoinResultDTO {
        private Long membershipId;
        private Long userId;
        private Long groupId;
        private String userNickname;
        private String groupName;
    }
    @Getter
    public static class LeaveDTO {
        private String userNickname;
        private String groupName;
    }

    @Getter @Setter @Builder
    public static class LeaveResultDTO {
        private Long membershipId;
        private Long userId;
        private Long groupId;
        private String userNickname;
        private String groupName;
    }
}
