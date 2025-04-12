package com.car.manager.repository.db;

import com.car.manager.repository.schema.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserSchema, Long> {
}
