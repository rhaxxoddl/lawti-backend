package com.oli.HometownPolitician.domain.user.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "message_token", unique = true)
    private String messageToken;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTagRelation> followedUserTagRelations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillUserRelation> followedBillUserRelations = new ArrayList<>();

    @Builder
    public User(String uuid) {
        this.uuid = uuid;
    }

    //    메서드명 getFollowingTags로 바꾸기
    public List<Tag> getFolloedTags() {
        return followedUserTagRelations
                .stream()
                .map(UserTagRelation::getTag)
                .collect(Collectors.toList());
    }

//    메서드명 followTags로 바꾸기
    public void followingTags(List<Tag> tags) {
        tags.forEach(tag -> {
            if (followedUserTagRelations.stream()
                    .noneMatch(followedTag -> (followedTag.getTag() == tag)))
                followedUserTagRelations.add(UserTagRelation
                        .builder()
                        .user(this)
                        .tag(   tag)
                        .build());
        });
    }

    //    메서드명 unfollowTags로 바꾸기
    public void unfollowingTags(List<Tag> tags) {
        tags.forEach(tag -> followedUserTagRelations.removeIf(followedTag ->
                (followedTag.getTag() == tag)
        ));
    }

    public List<Bill> getFollowingBills() {
        return followedBillUserRelations.stream()
                .filter(billUserRelation -> !billUserRelation.getIsUnfollowed())
                .map(BillUserRelation::getBill)
                .collect(Collectors.toList());
    }
}
