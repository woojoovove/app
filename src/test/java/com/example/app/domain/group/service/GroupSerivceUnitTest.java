package com.example.app.domain.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.GroupRepository;
import com.example.app.data.repository.GroupRepositorySupport;
import com.example.app.domain.group.dto.GroupDTO;
import com.example.app.domain.group.dto.GroupDTO.Create;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class GroupSerivceUnitTest {

    @InjectMocks
    private GroupService groupService;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private GroupRepositorySupport groupRepositorySupport;

    @BeforeEach
    void setUp() {
        ModelMapper modelmapper = new ModelMapper();
        groupService = new GroupService(groupRepository, groupRepositorySupport, modelmapper);
    }
    @Test
    @DisplayName("중복된 이름으로 알림 그룹 생성시 예외 발생")
    void failGroupCreationWhenDuplicateName() {

        when(groupRepository.findByName(anyString()))
            .thenReturn(Optional.of(GroupsEntity.builder()
            .name("name").build()));

        try{
            groupService.add(Create.builder().name("name").build());
        } catch(BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    @Test
    @DisplayName("알림 그룹 생성")
    void succedGroupCreation() {

        when(groupRepository.save(any())).thenReturn(GroupsEntity.builder().name("group").build());
        GroupDTO.Get created = groupService.add(Create.builder().name("group").build());

        assertThat(created).isNotNull();
        assertEquals(created.getName(), "group");
    }

    @Test
    @DisplayName("그룹명으로 유저 조회 - repo가 empty리스트를 반환할 때")
    void getEmptyUsersByGroupNameTest() {
        String now = new Date().toString();
        when(groupRepositorySupport.findUsersByGroupName(now))
            .thenReturn(new ArrayList<>());
        assertTrue(groupService.getUsersByGroupName(now).isEmpty());

    }

    @Test
    @DisplayName("그룹명으로 유저 조회 - repo가 리스트를 반환할 때")
    void throwFindingGroupByNonExistingName() {
        List<UsersEntity> list = List.of(UsersEntity.builder().nickname("nickname").build());
        when(groupRepositorySupport.findUsersByGroupName("nickname")).thenReturn(list);
        List<UsersEntity> found = groupService.getUsersByGroupName("nickname");
        assertFalse(found.isEmpty());
        assertEquals(found.get(0).getNickname(), list.get(0).getNickname());
    }

    @Test
    @DisplayName("존재하지 않는 그룹명 조회 시 예외 발생")
    void findByNameOrThrowTest() {
        when(groupRepository.findByName(anyString())).thenReturn(Optional.empty());
        try{
            groupService.findByNameOrThrow("any");
        } catch(BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.GROUP_NOT_FOUND);
        }
    }
}
