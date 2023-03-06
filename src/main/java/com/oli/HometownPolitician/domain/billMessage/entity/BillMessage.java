package com.oli.HometownPolitician.domain.billMessage.entity;

import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.BillStageType;
import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

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
    @Builder(builderClassName = "ByCurrentStageBuilder", builderMethodName = "ByCurrentStageBuilder")
    public BillMessage(Bill bill, BillStageType currentStage) {
        Assert.notNull(bill, "bill은 null이 될 수 없습니다");
        Assert.notNull(currentStage, "currentStage는 null이 될 수 없습니다");

        this.bill = bill;
        this.content = bill.getTitle() + getMessageByBillStage(currentStage);
    }

    static private String getMessageByBillStage(BillStageType stageType) {
        return switch (stageType) {
            case RECEIPT -> "이(가) 국회에 접수되었어요";
            case COMMITTEE_RECEIPT -> "이(가) 상임위원회에 접수되었어요";
            case COMMITTEE_REVIEW -> "이(가) 상임위원회에서 심사중이에요";
            case SYSTEMATIC_REVIEW -> "이(가) 법제사법위원회에서 체계·자구 심사중이에요";
            case MAIN_SESSION_AGENDA -> "이(가) 국회 본회의에 부의되었어요";
            case PROMULGATION -> "이(가) 공포되었어요";
            case MAIN_SESSION_REJECTION -> "이(가) 국회 본회의에 불부의되었어요";
            case ALTERNATIVE_DISCARD -> "이(가) 대안이 반영되어 폐기되었어요";
            case DISCARD -> "이(가) 폐기되었어요";
            case WITHDRAWAL -> "이(가) 철수되었어요";
        };
    }
}
