package com.semi.controllers.viewControllers;

import com.semi.models.Task;
import com.semi.models.User;
import com.semi.security.JwtTokenUtil;
import com.semi.services.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Date 21.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @GetMapping("/getAllTasks")
    public String getAllTasks(@CookieValue(value = "token") String token, ModelMap modelMap){
        User user = jwtTokenUtil.getUserFromToken(token);
        /*req.getHeader(tokenHeader)*/
        List<Task> tasks = taskService.getAllTasks(user);
        modelMap.addAttribute("tasks", tasks);
        return "tasksPage";
    }

    @PostMapping(value = "/addTask")
    public String addNewTask(@CookieValue(value = "token") String token, @Valid Task task, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0));
            return "redirect:/tasks/getAllTasks";
        }
        User user = jwtTokenUtil.getUserFromToken(token);
        task.setUser(user);
        taskService.addNewTask(task);
        return "redirect:/tasks/getAllTasks";
    }

}
