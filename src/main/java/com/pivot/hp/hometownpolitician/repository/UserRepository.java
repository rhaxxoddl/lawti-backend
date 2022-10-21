package com.pivot.hp.hometownpolitician.repository;

import com.pivot.hp.hometownpolitician.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.email = :identifier")
    User findByIdentifier(String identifier);

    @Query(value = "select count(u) > 0 from User u where u.email = :identifier")
    Boolean existsByIdentifier(String identifier);

}
