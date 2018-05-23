package com.semi.services.interfaces;

import com.semi.models.User;

import java.util.Optional;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface UserService {
    void addNewUser(User user);
    Optional<User> getUserByUsername(String username);
}
