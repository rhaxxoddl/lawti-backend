package com.oli.HometownPolitician.domain.proposer.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.proposer.enumeration.ProposerRole;
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
public class Proposer {
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
    private ProposerRole proposerRole;

    static public Proposer from(Bill bill, Politician politician, ProposerRole proposerRole) {
        return Proposer.builder()
                .bill(bill)
                .politician(politician)
                .proposerRole(proposerRole)
                .build();
    }
}
