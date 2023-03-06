package com.oli.HometownPolitician.domain.startMessage.entity;

import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "start_message")
@EntityListeners(AuditingEntityListener.class)
public class StartMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "start_message_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;
}
