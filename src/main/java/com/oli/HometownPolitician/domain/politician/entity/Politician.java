package com.oli.HometownPolitician.domain.politician.entity;

import com.oli.HometownPolitician.domain.proposer.entity.Proposer;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
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
@Table(name = "politicians")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "POLITICIAN_SEQ_GENERATOR", sequenceName = "POLITICIAN_SEQ")
public class Politician extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POLITICIAN_SEQ")
    @Column(name = "politician_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "chinese_name", nullable = false)
    private String chineseName;
    @Column(name = "party", nullable = false)
    private String party;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "politician")
    private List<Proposer> proposeList;
}
