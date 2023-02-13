package com.oli.HometownPolitician.domain.user.entity;

import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTagRelation> followedTags = new HashSet<>();

    @Builder
    public User(String uuid) {
        this.uuid = uuid;
    }

    public void followingTags(List<Tag> tags) {
        followedTags.addAll(tags
                .stream()
                .map(
                        tag -> UserTagRelation
                                .builder()
                                .user(this)
                                .tag(tag)
                                .build()
                )
                .collect(Collectors.toList())
        );
    }

    public void unfollowingTags(List<Long> tagIds) {
        tagIds.forEach(tagId -> {
            followedTags.removeIf(followedTag ->
                    (followedTag.getTag().getId().equals(tagId))
            );
        });
    }

}
