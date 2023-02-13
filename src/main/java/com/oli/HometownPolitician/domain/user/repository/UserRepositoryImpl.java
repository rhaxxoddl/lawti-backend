package com.oli.HometownPolitician.domain.user.repository;

import com.oli.HometownPolitician.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.oli.HometownPolitician.domain.user.entity.QUser.user;
import static com.oli.HometownPolitician.domain.userTagRelation.entity.QUserTagRelation.userTagRelation;

public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final UserRepositoryCond userCond;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.userCond = new UserRepositoryCond();
    }

    @Override
    public Optional<User> qFindByUuid(String uuid) {
        return queryFactory
                .selectFrom(user)
                .where(
                        userCond.userEqUuid(uuid)
                                .and(userCond.userNotDeleted())
                )
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> qFindByUuidWithFollowedTags(String uuid) {
        return queryFactory
                .selectFrom(user)
                .where(
                        userCond.userEqUuid(uuid)
                                .and(userCond.userNotDeleted())
                ).leftJoin(user.followedTags, userTagRelation)
                .fetchJoin()
                .stream().findFirst();
    }
}
