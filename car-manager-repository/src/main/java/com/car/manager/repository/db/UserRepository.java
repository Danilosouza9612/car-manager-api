package com.car.manager.repository.db;

import com.car.manager.repository.schema.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserSchema, Long> {
    Optional<UserSchema> findByLogin(String login);
}
