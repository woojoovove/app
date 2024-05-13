package com.example.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.domain.service.GroupService;
import com.example.app.domain.service.dto.GroupDTO;
import com.example.app.domain.service.dto.GroupDTO.Create;
import com.example.app.error.exception.BusinessException;
import com.example.app.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class GroupCreationTest {

	// TODO autowired 사용 이유
	@Autowired
	private GroupService groupService;

	@Test
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
	void succedGroupCreation() {
		GroupDTO.Create group = new Create();
		group.setName(String.valueOf(LocalDateTime.now()));
		GroupDTO.Get created = groupService.add(group);
		assertThat(created).isNotNull();
	}
}
