package com.oli.HometownPolitician.domain.proposer.entity;

import com.mysema.commons.lang.Assert;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
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
@Table(name = "proposers")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "PROPOSER_SEQ_GENERATOR", sequenceName = "PROPOSER_SEQ")
public class Proposer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROPOSER_SEQ")
    @Column(name = "proposer_id", unique = true, nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Bill bill;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "politician_id", referencedColumnName = "politician_id")
    private Politician politician;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProposerRole proposerRole;

    @Builder(builderClassName = "InitBuilder", builderMethodName = "InitBuilder")
    public Proposer(Bill bill, Politician politician, ProposerRole role) {
        Assert.notNull(bill, "bill에 null이 들어올 수 없습니다");
        Assert.notNull(politician, "politician에 null이 들어올 수 없습니다");
        Assert.notNull(role, "role에 null이 들어올 수 없습니다");

        this.bill = bill;
        this.politician = politician;
        proposerRole = role;
    }
}
