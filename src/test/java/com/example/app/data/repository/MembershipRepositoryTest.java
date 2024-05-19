package com.example.app.data.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.MembershipEntity;
import com.example.app.data.entity.UsersEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class MembershipRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private GroupRepository groupRepository;
    private MembershipRepositorySupport membershipRepositorySupport;
    private GroupRepositorySupport groupRepositorySupport;
    private UsersEntity user;
    private GroupsEntity group;
    private MembershipEntity membership;

    @BeforeEach
    void setUp() {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(testEntityManager.getEntityManager());
        membershipRepositorySupport = new MembershipRepositorySupport(jpaQueryFactory);
        groupRepositorySupport = new GroupRepositorySupport(jpaQueryFactory);

        user = usersRepository.save(UsersEntity.builder().nickname("nickname").build());
        group = groupRepository.save(GroupsEntity.builder().name("groupname").build());
        membership = membershipRepository.save(MembershipEntity.builder().user(user).group(group).build());
    }
    @Test
    @DisplayName("멤버십 레포의 멤버십 생성 확인")
    void joinGroupTest() {
        assertNotNull(membership);
        assertEquals(membership.getUser().getNickname(), user.getNickname());
        assertEquals(membership.getGroup().getName(), group.getName());
    }

    @Test
    @DisplayName("멤버십 레포의 멤버십 삭제 확인")
    void leaveGroupTest() {
        membershipRepository.delete(membership);
        Optional<MembershipEntity> deletedMembership =
            membershipRepository.findById(membership.getId());
        assertTrue(deletedMembership.isEmpty());
    }
    @Test
    @DisplayName("유저 엔티티와 그룹 엔티티로 멤버십 조회")
    void findExistingMembershipTest() {
        MembershipEntity foundMembership = membershipRepositorySupport
            .findByUserAndGroup(user, group).orElse(null);
        assertEquals(membership, foundMembership);
    }

    @Test
    @DisplayName("유저 엔티티와 그룹 엔티티로 멤버십 조회")
    void findNonExistingMembershipTest() {
        String now = new Date().toString();
        UsersEntity userNow = usersRepository.save(UsersEntity.builder().nickname(now).build());
        GroupsEntity groupNow = groupRepository.save(GroupsEntity.builder().name(now).build());
        Optional<MembershipEntity> optional =
            membershipRepositorySupport.findByUserAndGroup(userNow, groupNow);
        assertEquals(Optional.empty(), optional);
    }

    @Test
    @DisplayName("유저가 없는 그룹에서 유저 조회")
    void findNoUsersByExistingGroupName(){
        String now = new Date().toString();
        GroupsEntity groupNow = groupRepository.save(GroupsEntity.builder().name(now).build());
        assertTrue(groupRepositorySupport.findUsersByGroupName(groupNow.getName()).isEmpty());
    }

    @Test
    @DisplayName("유저가 있는 그룹에서 유저 조회")
    void findUsersByExistingGroupName(){
        assertEquals(groupRepositorySupport.findUsersByGroupName(group.getName()).get(0), user);
    }

    @Test
    @DisplayName("존재하지 않는 그룹에서 유저 조회")
    void findUsersByNonExistingGroupName(){
        assertTrue(groupRepositorySupport.findUsersByGroupName(new Date().toString()).isEmpty());
    }
}
