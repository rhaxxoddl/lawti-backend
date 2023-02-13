package com.oli.HometownPolitician.domain.tag.repository;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.repository.UserRepositoryCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oli.HometownPolitician.domain.tag.entity.QTag.tag;

public class TagRepositoryImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final UserRepositoryCond userCond;

    public TagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.userCond = new UserRepositoryCond();
    }

    @Override
    public List<Tag> queryTagsByIds(List<Long> tagIds) {
        return queryFactory
                .selectFrom(tag)
                .where(tag.id.in(tagIds))
                .fetch();
    }
}
