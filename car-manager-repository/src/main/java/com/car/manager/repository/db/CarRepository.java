package com.car.manager.repository.db;

import com.car.manager.repository.schema.CarSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarSchema, Long> {
    @Query("SELECT c FROM CarSchema c JOIN UserSchema u ON c.userSchema.id = u.id WHERE c.id = ?1 AND u.login = ?2")
    Optional<CarSchema> findByIdAndLogin(long id, String login);

    @Query("SELECT c FROM CarSchema c JOIN UserSchema u ON c.userSchema.id = u.id WHERE u.login = ?1")
    Page<CarSchema> findAllByLogin(String login, PageRequest pageRequest);

    boolean existsByLicensePlate(String licensePlate);
}
