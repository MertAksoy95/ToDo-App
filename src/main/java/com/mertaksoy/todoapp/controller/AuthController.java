package com.mertaksoy.todoapp.controller;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.LoginRequest;
import com.mertaksoy.todoapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the class where we handle Authentication related requests. API details are available in the service layer.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "This is for the user to login.")
    @ApiResponses(value = {@ApiResponse(responseCode = "401", description = "This is when the user or password is incorrect or the user cannot be found.")})
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
