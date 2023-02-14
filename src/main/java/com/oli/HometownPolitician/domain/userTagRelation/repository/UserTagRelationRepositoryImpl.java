package com.oli.HometownPolitician.domain.userTagRelation.repository;

import com.oli.HometownPolitician.domain.user.repository.UserRepositoryCond;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oli.HometownPolitician.domain.tag.entity.QTag.tag;
import static com.oli.HometownPolitician.domain.user.entity.QUser.user;
import static com.oli.HometownPolitician.domain.userTagRelation.entity.QUserTagRelation.userTagRelation;

public class UserTagRelationRepositoryImpl implements UserTagRelationRepositoryCustom{
    private final UserRepositoryCond userCond;
    private final JPAQueryFactory queryFactory;

    public UserTagRelationRepositoryImpl(EntityManager em) {
        this.userCond = new UserRepositoryCond();
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserTagRelation> qFindFollowedTagsByUuid(String uuid) {
        return queryFactory
                .selectFrom(userTagRelation)
                .where(
                        userTagRelation.user.uuid.eq(uuid)
                                .and(userCond.userNotDeleted())
                )
                .join(userTagRelation.user, user)
                .join(userTagRelation.tag, tag)
                .fetch();
    }
}
