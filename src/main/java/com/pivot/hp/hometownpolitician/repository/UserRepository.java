package com.pivot.hp.hometownpolitician.repository;

import com.pivot.hp.hometownpolitician.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
