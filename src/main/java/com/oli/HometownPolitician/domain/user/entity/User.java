package com.oli.HometownPolitician.domain.user.entity;

import com.oli.HometownPolitician.global.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "message_token", unique = true)
    private String messageToken;

    @Column(name = "uuid", unique = true)
    private String uuid;

}
