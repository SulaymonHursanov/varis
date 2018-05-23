package com.semi.controllers.viewControllers;

import com.semi.dto.CredentialsDTO;
import com.semi.models.User;
import com.semi.services.interfaces.LoginService;
import com.semi.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

/**
 * Date 23.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @GetMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid  User user, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute("error" , result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/signUp";
        }
        userService.addNewUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid  CredentialsDTO credentialsDTO, HttpServletResponse response){
        HttpHeaders headers = new HttpHeaders();
        String token = loginService.login(credentialsDTO, response);
        headers.set(tokenHeader, token );
        headers.setLocation(URI.create("/tasks/getAllTasks"));
        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/quit")
    public String logout(HttpServletResponse response){
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/login";
    }
}
