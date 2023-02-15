package com.oli.HometownPolitician.domain.userTagRelation.entity;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tag_relations")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "USERTAGRELATION_SEQ_GENERATOR", sequenceName = "USERTAGRELATION_SEQ")
public class UserTagRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERTAGRELATION_SEQ")
    @Column(name = "user_tag_relation_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", nullable = false)
    private Tag tag;

    @Builder
    public UserTagRelation(User user, Tag tag) {
        this.user = user;
        this.tag = tag;
    }
}
