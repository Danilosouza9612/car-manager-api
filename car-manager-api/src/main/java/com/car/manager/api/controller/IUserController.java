package com.car.manager.api.controller;

import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.user.UserCreationRequestDTO;
import com.car.manager.core.dto.user.UserDTO;
import com.car.manager.core.dto.user.UserFullDTO;
import com.car.manager.core.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    static String MESSAGE_404 = "Instance not found";
    static String MESSAGE_409 = "Email or login already exists";
    static String MESSAGE_422 = "Invalid data";

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", description = MESSAGE_422, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = MESSAGE_409, content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<UserFullDTO> createUser(UserCreationRequestDTO userDTO);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<UserResponseDTO> getUserById(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<PageContent<UserResponseDTO>> getAllUsers(int page, int perPage);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = MESSAGE_409, content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", description = MESSAGE_422, content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<UserResponseDTO> updateUser(Long id, UserDTO userDTO);

    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = MESSAGE_404, content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<Void> deleteUser(Long id);
}
