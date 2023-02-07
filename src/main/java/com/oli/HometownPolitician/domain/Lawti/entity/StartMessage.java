package com.oli.HometownPolitician.domain.Lawti.entity;

import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
