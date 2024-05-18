package com.example.app.domain.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.MembershipEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.GroupRepository;
import com.example.app.data.repository.MembershipRepository;
import com.example.app.data.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MembershipRepositoryTest {
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private GroupRepository groupRepository;
    private UsersEntity user;
    private GroupsEntity group;

    @BeforeEach
    void insertUserAndGroup() {
        user = usersRepository.save(UsersEntity.builder().nickname("nickname").build());
        group = groupRepository.save(GroupsEntity.builder().name("groupname").build());
    }
    @Test
    @DisplayName("멤버십 레포의 멤버십 생성 확인")
    void joinGroupTest() {
        MembershipEntity membership = membershipRepository.save(
            MembershipEntity.builder()
                .user(user)
                .group(group)
                .build()
        );
        assertNotNull(membership);
        assertEquals(membership.getUser().getNickname(), user.getNickname());
        assertEquals(membership.getGroup().getName(), group.getName());
    }

    @Test
    @DisplayName("멤버십 레포의 멤버십 삭제 확인")
    void nonUserJoin() {
        membershipRepository.delete(
            MembershipEntity.builder()
                .user(user)
                .group(group)
                .build()
        );
    }
}
