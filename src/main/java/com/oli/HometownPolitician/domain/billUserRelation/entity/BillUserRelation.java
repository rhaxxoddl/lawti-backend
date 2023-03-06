package com.oli.HometownPolitician.domain.billUserRelation.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
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
@Table(name = "bill_user_relations")
@SequenceGenerator(name = "BILLUSERRELATION_SEQ_GENERATOR", sequenceName = "BILLUSERRELATION_SEQ")
public class BillUserRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILLUSERRELATION_SEQ")
    @Column(name = "bill_user_relation_id", unique = true, nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id", nullable = false)
    private Bill bill;
    @Builder.Default
    @Column(name = "is_unfollowed", nullable = false)
    private Boolean isUnfollowed = false;
    @OneToOne
    @JoinColumn(name = "last_read_bill_message_id", referencedColumnName = "bill_message_id")
    private BillMessage lastReadBillMessage;

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public BillUserRelation(User user, Bill bill, Boolean isUnfollowed) {
        Assert.notNull(user, "user에 null이 들어올 수 없습니다.");
        Assert.notNull(bill, "bill에 null이 들어올 수 없습니다.");

        this.user = user;
        this.bill = bill;
        this.isUnfollowed = isUnfollowed;
        lastReadBillMessage = null;
    }
}
