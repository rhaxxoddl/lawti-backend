package com.oli.HometownPolitician.domain.committe.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "committees")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "COMMITTEE_SEQ_GENERATOR", sequenceName = "COMMITTEE_SEQ")
public class Committee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMITTEE_SEQ")
    @Column(name = "committee_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "external_committee_id", unique = true, nullable = false)
    private String external_committee_id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "committee")
    private List<Bill> bills;
}
