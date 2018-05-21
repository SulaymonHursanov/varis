package com.semi.repositories;

import com.semi.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date 21.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public interface TaskRepository extends JpaRepository<Task, Long> {
}
