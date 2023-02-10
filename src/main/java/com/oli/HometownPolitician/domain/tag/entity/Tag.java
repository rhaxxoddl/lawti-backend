package com.oli.HometownPolitician.domain.tag.entity;

import com.oli.HometownPolitician.domain.entityRelation.entity.UserTagRelation;
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
public class Tag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<UserTagRelation> followingUsers = new ArrayList<>();

    @Builder
    public Tag(String name) {
        this.name = name;
    }
}
