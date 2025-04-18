package com.car.manager.api.controller;

import com.car.manager.api.dto.ImageUploadedDTO;
import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICarController {
    static String MESSAGE_401 = "Invalid token";
    static String MESSAGE_403 = "Not allowed to use this endpoint";
    static String MESSAGE_404 = "Instance not found for authenticated user";
    static String MESSAGE_409 = "License plate already exists";
    static String MESSAGE_422 = "Invalid data";
    static String MESSAGE_422_INVALID_FILE = "Only JPEG or PNG are allowed";


    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", description = MESSAGE_422, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = MESSAGE_409, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = MESSAGE_403, content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<CarResponseDTO> create(CarDTO carDTO);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = MESSAGE_403, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<CarResponseDTO> read(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = MESSAGE_403, content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<PageContent<CarResponseDTO>> list(int page, int perPage);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = MESSAGE_403, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = MESSAGE_409, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", description = MESSAGE_422, content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<CarResponseDTO> update(Long id, CarDTO carDTO);

    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = MESSAGE_401, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = MESSAGE_403, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<Void> delete(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", description = MESSAGE_422_INVALID_FILE, content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<ImageUploadedDTO> uploadPhoto(Long id, @Parameter(description = "Supported File types: JPEG and PNG") MultipartFile file) throws IOException;
}
