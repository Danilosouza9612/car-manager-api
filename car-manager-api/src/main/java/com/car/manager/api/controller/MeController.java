package com.car.manager.api.controller;

import com.car.manager.core.dto.user.MeDTO;
import com.car.manager.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class MeController implements IMeController{
    private final UserService userService;

    public MeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<MeDTO> me(){
        return ResponseEntity.ok(
                userService.me((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
    }
}
