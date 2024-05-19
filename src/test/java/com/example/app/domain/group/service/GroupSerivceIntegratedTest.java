package com.example.app.domain.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.app.domain.group.dto.GroupDTO;
import com.example.app.domain.group.dto.GroupDTO.Create;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class GroupSerivceIntegratedTest {

    @Autowired
    private GroupService groupService;

    @Test
    @DisplayName("중복된 이름으로 알림 그룹 생성시 예외 발생")
    void failGroupCreationWhenDuplicateName() {
        GroupDTO.Create group = new Create();
        group.setName("duplicate");
        groupService.add(group);

        try{
            groupService.add(group);
        } catch(BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    @Test
    @DisplayName("알림 그룹 생성")
    void succedGroupCreation() {
        GroupDTO.Create group = new Create();
        group.setName(String.valueOf(LocalDateTime.now()));
        GroupDTO.Get created = groupService.add(group);
        assertThat(created).isNotNull();
    }
}
