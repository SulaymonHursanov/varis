package com.semi.services.interfaces;

import com.semi.models.Task;

import java.util.List;

/**
 * Date 21.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface TaskService {
    void addNewTask(Task task);
    List<Task> getAllTasks();
    Task updateTask(Task task);
    void deleteTask(Long id);
}
