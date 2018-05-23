package com.semi.services.implementations;

import com.semi.dto.CredentialsDTO;
import com.semi.models.User;
import com.semi.repositories.UserRepository;
import com.semi.security.JwtTokenUtil;
import com.semi.services.interfaces.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(CredentialsDTO credentialsDTO) {
        String username = credentialsDTO.getUsername();
        String rawPassword = credentialsDTO.getPassword();

        User user = userRepository.findFirstByUsername(username).orElseThrow(()
                -> new IllegalArgumentException("User not found by username <" + username + ">"));

        if (passwordEncoder.matches(rawPassword, user.getHashPassword())){
            return jwtTokenUtil.generateToken(user);
        }else
            throw new IllegalArgumentException("User username/password incorrect");
    }

    @Override
    public String login(CredentialsDTO credentialsDTO, HttpServletResponse response) {
        String username = credentialsDTO.getUsername();
        String rawPassword = credentialsDTO.getPassword();

        User user = userRepository.findFirstByUsername(username).orElseThrow(()
                -> new IllegalArgumentException("User not found by username <" + username + ">"));

        if (passwordEncoder.matches(rawPassword, user.getHashPassword())){
            String token = jwtTokenUtil.generateToken(user);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return token;
        }else
            throw new IllegalArgumentException("User username/password incorrect");
    }
}
