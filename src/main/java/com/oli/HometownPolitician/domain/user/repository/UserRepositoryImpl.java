package com.oli.HometownPolitician.domain.user.repository;

import com.oli.HometownPolitician.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.oli.HometownPolitician.domain.user.entity.QUser.user;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final UserRepositoryCond userCond;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.userCond = new UserRepositoryCond();
    }

    @Override
    public Optional<User> qFindByUuidWithTag(String userUuid) {
        return queryFactory
                .selectFrom(user)
                .where(
                        userCond.userEqUuid(userUuid)
                                .and(userCond.userNotDeleted())
                )
                .stream()
                .findAny();
    }
}
