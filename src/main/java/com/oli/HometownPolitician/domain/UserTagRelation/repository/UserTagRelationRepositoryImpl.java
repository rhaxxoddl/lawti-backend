package com.oli.HometownPolitician.domain.UserTagRelation.repository;

import com.oli.HometownPolitician.domain.UserTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.domain.user.repository.UserRepositoryCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oli.HometownPolitician.domain.UserTagRelation.entity.QUserTagRelation.userTagRelation;
import static com.oli.HometownPolitician.domain.tag.entity.QTag.tag;
import static com.oli.HometownPolitician.domain.user.entity.QUser.user;

public class UserTagRelationRepositoryImpl implements UserTagRelationRepositoryCustom{
    private final UserRepositoryCond userCond;
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public UserTagRelationRepositoryImpl(EntityManager em) {
        this.userCond = new UserRepositoryCond();
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
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

    @Override
    public void qFollowingTagByUuid(User user, List<Tag> tags) {
        List<UserTagRelation> userTagRelationList = tags
                .stream()
                .map(tag -> UserTagRelation
                        .builder()
                        .user(user)
                        .tag(tag)
                        .build())
                .toList();
        userTagRelationList.forEach(em::persist);
    }
}
