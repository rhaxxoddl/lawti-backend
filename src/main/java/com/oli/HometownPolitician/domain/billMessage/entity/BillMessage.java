package com.oli.HometownPolitician.domain.billMessage.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bill_messages")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "BILL_MESSAGE_SEQ_GENERATOR", sequenceName = "BILL_MESSAGE_SEQ")
public class BillMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILL_MESSAGE_SEQ")
    @Column(name = "bill_message_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "content", unique = true, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id", nullable = false)
    private Bill bill;

    static public BillMessage from(String content, Bill bill) {
        return BillMessage.builder()
                .content(content)
                .bill(bill)
                .build();
    }
}
