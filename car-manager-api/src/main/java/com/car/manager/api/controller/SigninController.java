package com.car.manager.api.controller;

import com.car.manager.api.dto.login.SigninRequestDTO;
import com.car.manager.api.dto.login.SigninResponseDTO;
import com.car.manager.api.security.SigninService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signin")
public class SigninController implements ISigninController{

    private SigninService signinService;

    public SigninController(SigninService signinService){
        this.signinService = signinService;
    }

    @PostMapping
    public ResponseEntity<SigninResponseDTO> signin(@RequestBody SigninRequestDTO requestDTO){
        return new ResponseEntity<>(signinService.signin(requestDTO), HttpStatus.OK);
    }
}
