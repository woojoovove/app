package com.example.app.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Getter
    public static class JoinGroup {
        private String userNickname;
        private String groupName;
    }
    @Getter @Setter
    public static class JoinGroupResult {
        private Long userId;
        private Long groupId;
        private String userNickname;
        private String groupName;
    }

}
