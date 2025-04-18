package com.car.manager.api.controller;

import com.car.manager.api.Util;
import com.car.manager.api.dto.ImageUploadedDTO;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
import com.car.manager.core.dto.user.UserFullDTO;
import com.car.manager.core.dto.user.UserResponseDTO;
import com.car.manager.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController implements IUserController{

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
    public ResponseEntity<PageContent<UserResponseDTO>> getAllUsers(
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

    @PostMapping("/{id}/upload_photo")
    public ResponseEntity<ImageUploadedDTO> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(new ImageUploadedDTO(
                userService.uploadPhoto(id, file.getInputStream(), Util.getExtension(Objects.requireNonNull(file.getContentType()))).toString()
        ), HttpStatus.OK);
    }
}
