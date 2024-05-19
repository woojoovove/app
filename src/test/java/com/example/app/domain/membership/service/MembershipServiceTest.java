package com.example.app.domain.membership.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.MembershipRepositorySupport;
import com.example.app.domain.group.service.GroupService;
import com.example.app.domain.membership.dto.MembershipDTO.JoinDTO;
import com.example.app.domain.membership.dto.MembershipDTO.LeaveDTO;
import com.example.app.domain.user.service.UserService;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * MembershipServiceTest 의도
 * 1. MembershipService가 의존하고 있는 대상에게
 *    예외를 받았을 때 이를 적절히 처리하는지 확인
 * 2. MembershipService가 예외를 던지는 상황 설명
 */
@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    @InjectMocks
    private MembershipService membershipService;
    @Mock
    private UserService userService;
    @Mock
    private GroupService groupService;
    @Mock
    private MembershipRepositorySupport membershipRepositorySupport;
    private static JoinDTO joinDTO;
    private static LeaveDTO leaveDTO;
    private final static String TEST_USER_NAME = "test-user-for-membership-service";
    private final static String TEST_GROUP_NAME = "test-group-for-membership-service";
    @BeforeEach
    void setUp(){
        joinDTO = JoinDTO.builder()
            .userNickname(TEST_USER_NAME)
            .groupName(TEST_GROUP_NAME).build();
        leaveDTO = LeaveDTO.builder()
            .userNickname(TEST_USER_NAME)
            .groupName(TEST_GROUP_NAME).build();
    }

    @Test
    @DisplayName("존재 하지 않는 유저 닉네임을 가입시키려고 할 때 USER_NOT_FOUND 예외 발생")
    void noUserExceptionWhileJoin() {
        when(userService.findByUserNameOrThrow(anyString())).thenThrow(new BusinessException(
            ErrorCode.USER_NOT_FOUND));

        try {
            membershipService.joinGroup(joinDTO);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.USER_NOT_FOUND);
        }
    }
    @Test
    @DisplayName("존재 하지 않는 그룹 이름을 가입시키려고 할 때 GROUP_NOT_FOUND 예외 발생")
    void noGroupExceptionWhileJoin() {
        when(groupService.findByNameOrThrow(anyString())).thenThrow(new BusinessException(
            ErrorCode.GROUP_NOT_FOUND));

        try {
            membershipService.joinGroup(joinDTO);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.GROUP_NOT_FOUND);
        }
    }

    @Test
    @DisplayName("존재 하지 않는 유저를 탈퇴 시키려 할 때 USER_NOT_FOUND 예외 발생")
    void noUserExceptionWhileLeave() {
        when(userService.findByUserNameOrThrow(anyString())).thenThrow(new BusinessException(
            ErrorCode.USER_NOT_FOUND));

        try {
            membershipService.leaveGroup(leaveDTO);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.USER_NOT_FOUND);
        }
    }
    @Test
    @DisplayName("존재 하지 않는 그룹에서 탈퇴시키려고 할 때 GROUP_NOT_FOUND 예외 발생")
    void noGroupExceptionWhileLeave() {
        when(groupService.findByNameOrThrow(anyString())).thenThrow(new BusinessException(
            ErrorCode.GROUP_NOT_FOUND));

        try {
            membershipService.leaveGroup(leaveDTO);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.GROUP_NOT_FOUND);
        }
    }
    @Test
    @DisplayName("그룹에 가입한 적이 없는데 탈퇴하려 할 때 MEMBERSHIP_NOT_FOUND 예외 발생")
    void noMembershipExceptionWhileLeave() {
        UsersEntity user = UsersEntity.builder()
            .id(1L)
            .nickname(TEST_USER_NAME)
            .build();

        GroupsEntity group = GroupsEntity.builder()
            .id(1L)
            .name(TEST_GROUP_NAME)
            .build();

        when(userService.findByUserNameOrThrow(TEST_USER_NAME)).thenReturn(user);
        when(groupService.findByNameOrThrow(TEST_GROUP_NAME)).thenReturn(group);
        when(membershipRepositorySupport.findByUserAndGroup(
            user, group)).thenReturn(Optional.empty());

        try {
            membershipService.leaveGroup(leaveDTO);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.MEMBERSHIP_NOT_FOUND);
        }
    }
    @Test
    @DisplayName("그룹에 가입한 적이 없는데 멤버십 조회 할 때 MEMBERSHIP_NOT_FOUND 예외 발생")
    void noMembershipException() {
        UsersEntity user = UsersEntity.builder()
            .id(1L)
            .nickname(TEST_USER_NAME)
            .build();

        GroupsEntity group = GroupsEntity.builder()
            .id(1L)
            .name(TEST_GROUP_NAME)
            .build();

        when(membershipRepositorySupport.findByUserAndGroup(
            user, group)).thenReturn(Optional.empty());

        try {
            membershipService.findByUserIdAndGroupIdOrThrow(user, group);
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.MEMBERSHIP_NOT_FOUND);
        }
    }
}
