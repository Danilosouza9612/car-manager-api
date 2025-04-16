package com.car.manager.core.domain;

import com.car.manager.core.security.PasswordEncryptor;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    private User subject;

    @BeforeEach
    public void setup(){
        this.subject = Instancio.of(User.class).generate(field(User::getPassword), gen -> gen.string().maxLength(10)).create();
    }

    @Test
    public void testEncryptPassword(){
        String oldPassword = subject.getPassword();
        PasswordEncryptor passwordEncryptor = (password) -> "enc" + password + "enc";

        subject.encryptPassword(passwordEncryptor);
        String newPassword = subject.getPassword();

        assertNotEquals(oldPassword, newPassword);
    }

    @Test
    public void testUpdateLogin(){
        LocalDateTime oldLastLogin = subject.getLastLogin();

        subject.updateLastLogin();
        LocalDateTime newLastLogin = subject.getLastLogin();

        assertNotEquals(oldLastLogin, newLastLogin);
    }
}
