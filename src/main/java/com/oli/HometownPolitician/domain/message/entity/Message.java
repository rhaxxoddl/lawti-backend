package com.oli.HometownPolitician.domain.message.entity;

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
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "MESSAGE_SEQ_GENERATOR", sequenceName = "MESSAGE_SEQ")
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    @Column(name = "message_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "content", unique = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Bill bill;
}
