package com.oli.HometownPolitician.domain.billUserRelation.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.billMessage.entity.BillMessage;
import com.oli.HometownPolitician.domain.user.entity.User;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bill_user_relations")
@EntityListeners(AuditingEntityListener.class)
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
    @Column(name = "isUnfollowed", nullable = false)
    private Boolean isUnfollowed;
    @OneToOne
    @JoinColumn(name = "last_read_bill_message_id", referencedColumnName = "bill_message_id")
    private BillMessage lastReadBillMessage;

    @PrePersist
    public void prePersist(){
        this.isUnfollowed = this.isUnfollowed != null && this.isUnfollowed;
    }
}
