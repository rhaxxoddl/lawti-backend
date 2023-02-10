package com.oli.HometownPolitician.domain.user.repository;

import com.oli.HometownPolitician.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
