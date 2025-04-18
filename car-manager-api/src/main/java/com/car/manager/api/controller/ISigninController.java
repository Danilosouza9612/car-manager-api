package com.car.manager.api.controller;


import com.car.manager.api.dto.login.SigninRequestDTO;
import com.car.manager.api.dto.login.SigninResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface ISigninController {
    static String MESSAGE_401 = "Invalid credentials";

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<SigninResponseDTO> signin(SigninRequestDTO requestDTO);
}
