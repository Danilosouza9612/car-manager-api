package com.car.manager.core.service;

import com.car.manager.core.domain.User;
import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.gateway.UserGateway;
import com.car.manager.core.mapper.UserDTOMapper;
import com.car.manager.core.mapper.UserDTOMapperImpl;
import com.car.manager.core.security.PasswordEncryptor;
import com.car.manager.core.storage.FileStorage;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserGateway gateway;

    @Mock
    private AvatarService avatarService;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    private UserDTOMapper mapper;

    private UserService subject;

    @BeforeEach
    public void setup(){
        this.mapper = new UserDTOMapperImpl();
        this.subject = new UserService(gateway, mapper, passwordEncryptor, avatarService);
    }

    @Test
    public void create_WhenItValid(){
        User user = mockUserInstance();
        when(gateway.create(any(User.class))).thenReturn(user);
        when(gateway.existsByLogin(eq(user.getLogin()))).thenReturn(false);
        UserCreationRequestDTO request = mapper.toUserCreationRequestDTO(user);

        UserDTO response = subject.create(request);

        assertInstances(request, response);
    }

    @Test
    public void  notCreate_WhenLoginExists(){
        User user = mockUserInstance();
        when(gateway.existsByLogin(eq(user.getLogin()))).thenReturn(true);
        UserCreationRequestDTO request = mapper.toUserCreationRequestDTO(user);

        assertThrows(UniqueValueException.class, () -> subject.create(request));
    }

    @Test
    public void  notCreate_WhenEmailExists(){
        User user = mockUserInstance();
        when(gateway.existsByEmail(eq(user.getEmail()))).thenReturn(true);
        UserCreationRequestDTO request = mapper.toUserCreationRequestDTO(user);

        assertThrows(UniqueValueException.class, () -> subject.create(request));
    }

    @Test
    public void update_WhenItValid(){
        User user = mockUserInstance();
        when(gateway.save(any(User.class))).thenReturn(user);
        when(gateway.findById(eq(user.getId()))).thenReturn(Optional.of(user));
        UserCreationRequestDTO request = mapper.toUserCreationRequestDTO(user);

        UserDTO response = subject.update(user.getId(), request);

        assertInstances(request, response);
    }

    @Test
    public void notUpdate_WhenNotFound(){
        User user = mockUserInstance();

        when(gateway.findById(eq(user.getId()))).thenReturn(Optional.empty());
        UserCreationRequestDTO request = mapper.toUserCreationRequestDTO(user);

        assertThrows(InstanceNotFoundException.class, () -> subject.update(user.getId(), request));
    }

    @Test
    public void notUpdate_WhenEmailExists(){
        User user = mockUserInstance();
        UserCreationRequestDTO request = mapper.toUserCreationRequestDTO(user);
        when(gateway.findById(eq(user.getId()))).thenReturn(Optional.of(user));
        when(gateway.existsByEmail(eq(request.getEmail()))).thenReturn(true);

        assertThrows(UniqueValueException.class, () -> subject.update(user.getId(), request));
    }

    @Test
    public void delete_WhenExists(){
        User user = mockUserInstance();
        when(gateway.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        subject.delete(user.getId());

        verify(gateway).delete(eq(user.getId()));
    }

    @Test
    public void notDelete_whenNotFound(){
        when(gateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> subject.delete(1L));
    }

    @Test
    public void read_WhenExists(){
        User user = mockUserInstance();
        when(gateway.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        UserDTO userDTO = subject.read(user.getId());

        assertInstances(mapper.toDto(user), userDTO);
    }

    @Test
    public void read_WhenNotFound(){
        when(gateway.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> subject.read(1L));
    }

    @Test
    public void updateLogin_WhenExists(){
        User user = mock(User.class);
        when(gateway.findByLogin(user.getLogin())).thenReturn(Optional.of(user));

        subject.updateLogin(user.getLogin());

        verify(user).updateLastLogin();
    }

    @Test
    public void updateLogin_WhenNotFound(){
        when(gateway.findByLogin(anyString())).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> subject.updateLogin(""));
    }

    private User mockUserInstance(){
        return Instancio.of(User.class)
                .generate(field(User::getFirstName), gen -> gen.string().maxLength(10))
                .generate(field(User::getLastName), gen -> gen.string().maxLength(10))
                .generate(field(User::getEmail), gen -> gen.net().email())
                .generate(field(User::getLogin), gen -> gen.string().maxLength(25))
                .generate(field(User::getPassword), gen -> gen.string().maxLength(150))
                .generate(field(User::getPhone), gen -> gen.string().numericSequence().minLength(10).maxLength(11))
                .create();
    }

    private void assertInstances(UserDTO request, UserDTO response){
        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getBirthday(), response.getBirthday());
        assertEquals(request.getPhone(), response.getPhone());
    }
}
