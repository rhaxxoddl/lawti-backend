package com.oli.HometownPolitician.domain.politician.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "politicians")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "POLITICIAN_SEQ_GENERATOR", sequenceName = "POLITICIAN_SEQ")
public class Politician {
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
}
