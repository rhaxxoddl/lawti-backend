package com.oli.HometownPolitician.domain.billTagRelation.entity;

import com.mysema.commons.lang.Assert;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.tag.entity.Tag;
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
@Table(name = "bill_tag_relations")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "BILLTAGRELATION_SEQ_GENERATOR", sequenceName = "BILLTAGRELATION_SEQ")
public class BillTagRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILLTAGRELATION_SEQ")
    @Column(name = "bill_tag_relation_id", unique = true, nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", nullable = false)
    private Tag tag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id", nullable = false)
    private Bill bill;

    static public BillTagRelation from(Bill bill, Tag tag) {
        return BillTagRelation.builder()
                .bill(bill)
                .tag(tag)
                .build();
    }

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public BillTagRelation(Tag tag, Bill bill) {
        Assert.notNull(tag, "Tag에 null이 들어올 수 없습니다");
        Assert.notNull(bill, "Bill에 null이 들어올 수 없습니다");

        this.tag = tag;
        this.bill = bill;
    }
}
