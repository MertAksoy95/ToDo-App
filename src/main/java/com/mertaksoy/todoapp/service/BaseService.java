package com.mertaksoy.todoapp.service;

import com.mertaksoy.todoapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BaseService {

    /**
     * This method returns the id of the currently logged in user.
     */
    public String getLoggedInUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

}
