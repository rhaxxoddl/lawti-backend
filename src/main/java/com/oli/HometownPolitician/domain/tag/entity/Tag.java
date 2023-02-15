package com.oli.HometownPolitician.domain.tag.entity;

import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "TAG_SEQ_GENERATOR", sequenceName = "TAG_SEQ")
public class Tag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQ")
    @Column(name = "tag_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    private List<UserTagRelation> followingUsers = new ArrayList<>();

    @Builder
    public Tag(String name) {
        this.name = name;
    }
}
