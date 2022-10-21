package com.pivot.hp.hometownpolitician.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Length(min = 1, max = 8, message = "nickname must in 1 to 8 characters")
    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.ROLE_USER;

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

}

