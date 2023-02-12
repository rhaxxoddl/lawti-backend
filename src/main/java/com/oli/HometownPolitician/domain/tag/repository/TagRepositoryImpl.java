package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.repository.UserRepositoryCond;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.oli.HometownPolitician.domain.tag.entity.QTag.tag;
import static com.oli.HometownPolitician.domain.user.entity.QUser.user;
import static com.oli.HometownPolitician.domain.UserTagRelation.entity.QUserTagRelation.userTagRelation;

import javax.persistence.EntityManager;
import java.util.List;

public class TagRepositoryImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final UserRepositoryCond userCond;

    public TagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.userCond = new UserRepositoryCond();
    }

    @Override
    public List<Tag> qFindFollowedTagsByUserUuid(String userUuid) {
        return queryFactory
                .selectFrom(tag)
                .leftJoin(tag.followingUsers, userTagRelation)
                .leftJoin(userTagRelation.user, user)
                .where(
                       userCond.userEqUuid(userUuid)
                )
                .fetch();
    }
}
