package com.car.manager.repository.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserSchema {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String firstName;
}
