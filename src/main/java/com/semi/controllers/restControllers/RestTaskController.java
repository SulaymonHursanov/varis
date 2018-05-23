package com.semi.controllers.restControllers;

import com.semi.models.Task;
import com.semi.models.User;
import com.semi.security.JwtTokenUtil;
import com.semi.services.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Date 21.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@RestController
@RequestMapping("/api/tasks")
public class RestTaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Task>> getAllTasks(HttpServletRequest req){
        User user = jwtTokenUtil.getUserFromToken(req.getHeader(tokenHeader));
        return ResponseEntity.ok(taskService.getAllTasks(user));
    }

    @PostMapping(value = "/addTask", consumes = "application/json")
    public ResponseEntity addNewTask(@RequestBody @Valid Task task, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        taskService.addNewTask(task);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

/*
* public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setUsername(body.getSubject());
            u.setId(Long.parseLong((String) body.get("userId")));
            u.setRole((String) body.get("role"));

            return u;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

* */