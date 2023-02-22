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

    public List<Tag> getFollowingTags() {
        return followedUserTagRelations
                .stream()
                .map(UserTagRelation::getTag)
                .collect(Collectors.toList());
    }

    public void followTags(List<Tag> tags) {
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

    public void unfollowTags(List<Tag> tags) {
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

    public List<Bill> followBills(List<Bill> bills) {
        bills.forEach(bill -> {
            List<BillUserRelation> billUserRelations = followedBillUserRelations.stream()
                    .filter(billUserRelation -> (billUserRelation.getBill().getId().equals(bill.getId())))
                    .toList();
            if (billUserRelations.size() == 0) {
                BillUserRelation billUserRelation = BillUserRelation.builder()
                        .user(this)
                        .bill(bill)
                        .isUnfollowed(false)
                        .build();
                followedBillUserRelations.add(billUserRelation);
                bill.getFollowedBillUserRelations().add(billUserRelation);
            }
            else if (billUserRelations.size() > 1)
                throw new Error("해당 법안과 유저의 관계가 중복되었습니다");
            else
                billUserRelations.get(0).setIsUnfollowed(false);
        });
        return getFollowingBills();
    }

    public List<Bill> unfollowBills(List<Bill> bills) {
        bills.forEach(bill -> {
            List<BillUserRelation> billUserRelations = followedBillUserRelations.stream()
                    .filter(billUserRelation -> (billUserRelation.getBill().getId().equals(bill.getId())))
                    .toList();
            if (billUserRelations.size() == 0)
                followedBillUserRelations.add(
                        BillUserRelation.builder()
                                .user(this)
                                .bill(bill)
                                .isUnfollowed(true)
                                .build()
                );
            else if (billUserRelations.size() > 1)
                throw new Error("해당 법안과 유저의 관계가 중복되었습니다");
            else
                billUserRelations.get(0).setIsUnfollowed(true);
        });
        return getFollowingBills();
    }
}
