package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.LoginRequest;
import com.mertaksoy.todoapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * This is the class where Authentication related operations are performed.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    /**
     * This generates and returns JWT tokens for Users to authenticate.
     *
     * @param loginRequest LoginRequest contains username and password fields.
     *                     According to this information, the user is checked.
     */
    public ResponseEntity<BaseResponse> login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse("The username or password is incorrect or the user could not be found."));
        }

        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", token));
    }

}
