package com.semi.controllers.restControllers;

import com.semi.dto.CredentialsDTO;
import com.semi.models.User;
import com.semi.services.interfaces.LoginService;
import com.semi.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/

@Controller
@RequestMapping("/api")
public class RestAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.addNewUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody CredentialsDTO credentialsDTO){
       return ResponseEntity.ok( loginService.login(credentialsDTO));
    }
}
