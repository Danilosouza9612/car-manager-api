package com.car.manager.api.controller;

import com.car.manager.core.dto.user.MeDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface IMeController {
    static String MESSAGE_401 = "Invalid token";
    static String MESSAGE_403 = "Not allowed to use this endpoint";

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = MESSAGE_403, content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<MeDTO> me();

}
