package com.example.app.data.repository;

import static com.example.app.data.entity.QGroupsEntity.groupsEntity;
import static com.example.app.data.entity.QMembershipEntity.membershipEntity;
import static com.example.app.data.entity.QUsersEntity.usersEntity;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public GroupRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(GroupsEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<UsersEntity> findUsersByGroupName(String groupName) {
        return jpaQueryFactory.selectFrom(usersEntity)
            .join(usersEntity.userGroups, membershipEntity)
            .join(membershipEntity.group, groupsEntity)
            .where(groupsEntity.name.eq(groupName)).fetch();

    }
}
