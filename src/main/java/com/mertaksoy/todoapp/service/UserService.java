package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.dto.BaseResponse;
import com.mertaksoy.todoapp.dto.UserDto;
import com.mertaksoy.todoapp.entity.User;
import com.mertaksoy.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This is the class where User related operations are performed.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepo;

    // private final HazelcastInstance instance = Hazelcast.newHazelcastInstance();
    // private final IMap<String, User> sessionMap = instance.getMap("SessionIdMappingInstance");

    /**
     * This lists all users.
     *
     * @param page    The pagination page
     * @param size    The page size
     * @param sortDir The pagination sort direction
     * @param sort    The pagination sorting parameter
     */
    public ResponseEntity<BaseResponse> list(int page, int size, String sortDir, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", userRepo.findAll(pageRequest)));
    }

    /**
     * This returns the user based on the given id parameter.
     *
     * @param id User id
     */
    public ResponseEntity<BaseResponse> get(String id) {
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No user found for this id: " + id));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", existingUser));
    }

    /**
     * This creates a new user.
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

    /**
     * This updates the existing user.
     * User username are unique.
     * If there is a user with the same username, it returns a conflict response.
     *
     * @param id      User id
     * @param userDto There are username, password, firstName and lastName fields for the user.
     */
    public ResponseEntity<BaseResponse> update(String id, UserDto userDto) {
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No user found for this id: " + id));
        }

        if (!existingUser.getUsername().equalsIgnoreCase(userDto.getUsername())) {
            User user = userRepo.findByUsername(userDto.getUsername());
            if (user != null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new BaseResponse("This username address is already in use: " + userDto.getUsername()));
            }
        }

        existingUser.setUsername(userDto.getUsername());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());

        // sessionMap.remove(existingUser.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful", userRepo.save(existingUser)));
    }

    /**
     * It deletes the user belonging to the given user id.
     *
     * @param id User id
     */
    public ResponseEntity<BaseResponse> delete(String id) {
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse("No user found for this id: " + id));
        }

        userRepo.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse("Successful"));
    }

}
