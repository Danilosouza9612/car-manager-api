package com.car.manager.api.runner;

import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.schema.UserSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AppRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.existsByLogin("root")) return;
        String login = "root";
        String password = "123456";

        UserSchema userSchema = new UserSchema();
        userSchema.setFirstName("root");
        userSchema.setLastName("root");
        userSchema.setPhone("12345678911");
        userSchema.setEmail("root@mail.com");
        userSchema.setLogin("root");
        userSchema.setBirthday(LocalDate.now());
        userSchema.setPassword(passwordEncoder.encode(password));

        userRepository.saveAndFlush(userSchema);

        logger.info("User added. Login: {}, Password: {}", login, password);
    }
}
