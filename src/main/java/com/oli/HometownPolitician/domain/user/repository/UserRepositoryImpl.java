package com.oli.HometownPolitician.domain.user.repository;

import static com.oli.HometownPolitician.domain.user.entity.QUser.user;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

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
}
