package com.car.manager.api.controller;

import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.schema.UserSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public List<UserSchema> helloWorld(){
        UserSchema userSchema = new UserSchema();
        userSchema.setFirstName("Danilo");
        userRepository.saveAndFlush(userSchema);
        return userRepository.findAll();
    }
}
