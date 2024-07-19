package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.LoginRequest;
import com.mertaksoy.todoapp.dto.UserDto;
import com.mertaksoy.todoapp.entity.User;
import com.mertaksoy.todoapp.repository.UserRepository;
import com.mertaksoy.todoapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper mapper;
    private final UserRepository userRepo;


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

    /**
     * This public api for registration.
     * User username are unique.
     * If there is a user with the same username, it returns a conflict response.
     *
     * @param userDto There are username, password, firstName and lastName fields for the user.
     */
    public ResponseEntity<BaseResponse> create(UserDto userDto) {
        User existingUser = userRepo.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new BaseResponse("This username address is already in use: " + userDto.getUsername()));
        }

        User user = mapper.map(userDto, User.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", userRepo.save(user)));
    }

}
