package com.oli.HometownPolitician.domain.user.entity;

import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "message_token", unique = true)
    private String messageToken;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @OneToMany(mappedBy = "user")
    private List<UserTagRelation> followedTags = new ArrayList<>();

    @Builder
    public User(String uuid) {
        this.uuid = uuid;
    }

}
