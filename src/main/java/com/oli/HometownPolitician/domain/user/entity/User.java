package com.oli.HometownPolitician.domain.user.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.billUserRelation.entity.BillUserRelation;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import com.oli.HometownPolitician.global.error.NotFoundError;
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
    private List<UserTagRelation> userTagRelations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillUserRelation> billUserRelations = new ArrayList<>();

    @Builder
    public User(String uuid) {
        this.uuid = uuid;
    }

    public List<Tag> getFollowingTags() {
        return userTagRelations
                .stream()
                .map(UserTagRelation::getTag)
                .collect(Collectors.toList());
    }

    public void followTags(List<Tag> tags) {
        tags.forEach(tag -> {
            if (userTagRelations.stream()
                    .noneMatch(followedTag -> (followedTag.getTag() == tag)))
                userTagRelations.add(UserTagRelation
                        .builder()
                        .user(this)
                        .tag(tag)
                        .build());
        });
    }

    public void unfollowTags(List<Tag> tags) {
        tags.forEach(tag -> userTagRelations.removeIf(followedTag ->
                (followedTag.getTag() == tag)
        ));
    }

    public List<Bill> getFollowingBills() {
        return billUserRelations.stream()
                .filter(billUserRelation -> !billUserRelation.getIsUnfollowed())
                .map(BillUserRelation::getBill)
                .collect(Collectors.toList());
    }

    public List<Bill> followBills(List<Bill> bills) {
        bills.forEach(bill -> {
            List<BillUserRelation> target = billUserRelations.stream()
                    .filter(billUserRelation -> (billUserRelation.getBill().getId().equals(bill.getId())))
                    .toList();
            if (target.size() == 0) {
                BillUserRelation billUserRelation = BillUserRelation.InitBuilder()
                        .user(this)
                        .bill(bill)
                        .isUnfollowed(false)
                        .build();
                billUserRelations.add(billUserRelation);
                bill.getBillUserRelations().add(billUserRelation);
            } else if (target.size() > 1)
                throw new Error("해당 법안과 유저의 관계가 중복되었습니다");
            else
                target.get(0).setIsUnfollowed(false);
            bill.increaseFollowerCount();
        });
        return getFollowingBills();
    }

    public List<Bill> unfollowBills(List<Bill> bills) {
        bills.forEach(bill -> {
            List<BillUserRelation> target = billUserRelations.stream()
                    .filter(billUserRelation -> (billUserRelation.getBill().getId().equals(bill.getId())))
                    .toList();
            if (target.size() == 0)
                billUserRelations.add(
                        BillUserRelation.InitBuilder()
                                .user(this)
                                .bill(bill)
                                .isUnfollowed(true)
                                .build()
                );
            else if (target.size() > 1)
                throw new Error("해당 법안과 유저의 관계가 중복되었습니다");
            else
                target.get(0).setIsUnfollowed(true);
            bill.decreaseFollowerCount();
        });
        return getFollowingBills();
    }

    public void readBillMessage(BillMessage lastReadBillMessage) {
        if (lastReadBillMessage == null)
            throw new IllegalArgumentException("lastReadBillMessage로 null이 들어왔습니다");
        BillUserRelation billUserRelation = this.billUserRelations.stream().filter(followedBillUserRelation ->
                followedBillUserRelation.getIsUnfollowed().equals(false) &&
                        followedBillUserRelation.getBill().getId().equals(lastReadBillMessage.getBill().getId())
        ).findFirst().orElseThrow(()->new NotFoundError("해당 법안은 팔로우하고 있지 않습니다"));
        billUserRelation.setLastReadBillMessage(lastReadBillMessage);
    }

    public Long numOfUnreadBillMessage() {
        // TODO c
        // 매개변수를 어떤 타입으로 받을지 고민
        return null;
    }
}
