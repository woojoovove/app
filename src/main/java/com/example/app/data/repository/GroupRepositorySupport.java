package com.example.app.data.repository;

import static com.example.app.data.entity.QGroupsEntity.groupsEntity;

import com.example.app.data.entity.GroupsEntity;
import com.querydsl.core.types.Projections;
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

    public List<GroupsEntity> getGroupsByGroupNames(List<String> groupNames) {
        return jpaQueryFactory.select(Projections.bean(GroupsEntity.class))
            .from(groupsEntity)
            .where(groupsEntity.name.in(groupNames)).fetch();
    }
}
