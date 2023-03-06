package com.oli.HometownPolitician.domain.userTagRelation.entity;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_tag_relations")
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

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public UserTagRelation(User user, Tag tag) {
        Assert.notNull(user, "user가 null이 될 수 없습니다");
        Assert.notNull(user, "tag가 null이 될 수 없습니다");

        this.user = user;
        this.tag = tag;
    }
}
