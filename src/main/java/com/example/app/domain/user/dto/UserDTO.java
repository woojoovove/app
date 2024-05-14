package com.example.app.domain.user.dto;

import com.example.app.data.entity.GroupsEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Getter
    public static class JoinGroup {
        private String userNickname;
        private String groupName;
    }
    @Getter @Setter @Builder
    public static class JoinGroupResult {
        private Long userId;
        private Long groupId;
        private String userNickname;
        private String groupName;
    }
    @Getter
    public static class LeaveGroup {
        private String userNickname;
    }

    @Getter @Setter @Builder
    public static class LeaveGroupResult {
        private Long userId;
        private GroupsEntity group;
        private String userNickname;
    }
}
