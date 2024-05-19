package com.example.app.data.repository;

import static com.example.app.data.entity.QMembershipEntity.membershipEntity;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.MembershipEntity;
import com.example.app.data.entity.UsersEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class MembershipRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public MembershipRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(MembershipEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<MembershipEntity> findByUserAndGroup(UsersEntity user, GroupsEntity group) {
        return jpaQueryFactory.selectFrom(membershipEntity)
            .where(membershipEntity.user.eq(user)
                .and(membershipEntity.group.eq(group))).stream().findFirst();
    }
}
