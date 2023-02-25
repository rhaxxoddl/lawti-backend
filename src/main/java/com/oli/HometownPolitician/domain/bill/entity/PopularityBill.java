package com.oli.HometownPolitician.domain.bill.entity;

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
@Table(name = "popularity_bills")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "POPULARITY_BILL_SEQ_GENERATOR", sequenceName = "POPULARITY_BILL_SEQ")
public class PopularityBill extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POPULARITY_BILL_SEQ")
    @Column(name = "popularity_bill_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "number_of_follower")
    private Long numberOfFollower;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Bill bill;
}
