package com.car.manager.api.controller;

import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
import com.car.manager.core.dto.user.UserFullDTO;
import com.car.manager.core.dto.user.UserResponseDTO;
import com.car.manager.core.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"*"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserFullDTO> createUser(@Valid @RequestBody UserCreationRequestDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage
    ) {
        return ResponseEntity.ok(userService.list(page, perPage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
