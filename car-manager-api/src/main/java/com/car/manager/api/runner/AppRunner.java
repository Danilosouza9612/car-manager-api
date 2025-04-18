package com.car.manager.api.runner;

import com.car.manager.repository.db.UserRepository;
import com.car.manager.repository.schema.UserSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AppRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final String rootPassword;

    @Autowired
    public AppRunner(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${com.car.manager.root.password}") String rootPassword
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rootPassword = rootPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.existsByLogin("root")) return;

        UserSchema userSchema = new UserSchema();
        userSchema.setFirstName("root");
        userSchema.setLastName("root");
        userSchema.setPhone("12345678911");
        userSchema.setEmail("root@mail.com");
        userSchema.setLogin("root");
        userSchema.setBirthday(LocalDate.now());
        userSchema.setPassword(passwordEncoder.encode(rootPassword));

        userRepository.saveAndFlush(userSchema);

        logger.info("User added");
    }
}
