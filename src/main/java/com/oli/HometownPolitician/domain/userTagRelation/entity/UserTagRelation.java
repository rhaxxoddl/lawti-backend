package com.oli.HometownPolitician.domain.userTagRelation.entity;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tag_relations")
@EntityListeners(AuditingEntityListener.class)
public class UserTagRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_tag_relation_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag", referencedColumnName = "tag_id", nullable = false)
    private Tag tag;

    @Builder
    public UserTagRelation(User user, Tag tag) {
        this.user = user;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTagRelation that = (UserTagRelation) o;
        return Objects.equals(user, that.user) && Objects.equals(tag, that.tag);
    }
}
