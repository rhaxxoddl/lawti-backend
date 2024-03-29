package com.oli.HometownPolitician.domain.tag.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.billTagRelation.entity.BillTagRelation;
import com.oli.HometownPolitician.domain.userTagRelation.entity.UserTagRelation;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tags")
@SequenceGenerator(name = "TAG_SEQ_GENERATOR", sequenceName = "TAG_SEQ")
public class Tag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQ")
    @Column(name = "tag_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserTagRelation> userTagRelations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BillTagRelation> billTagRelations = new ArrayList<>();
    @PrePersist
    public void setup() {
        if (this.userTagRelations == null)
            this.userTagRelations = new ArrayList<>();
        if (this.billTagRelations == null)
            this.billTagRelations = new ArrayList<>();
    }

    public void addBill(Bill bill) {
        BillTagRelation billTagRelation = BillTagRelation.builder()
                .bill(bill)
                .tag(this)
                .build();
        this.billTagRelations.add(billTagRelation);
        bill.getBillTagRelations().add(billTagRelation);
    }
}
