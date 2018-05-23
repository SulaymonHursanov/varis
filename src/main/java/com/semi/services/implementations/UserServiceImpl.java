package com.semi.services.implementations;

import com.semi.models.User;
import com.semi.repositories.UserRepository;
import com.semi.security.role.Role;
import com.semi.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addNewUser(User user) {
        user.setRole(Role.USER);
        user.setHashPassword(passwordEncoder.encode(user.getHashPassword()));
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
       return userRepository.findFirstByUsername(username);
    }
}
