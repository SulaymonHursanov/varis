package com.semi.models;

import com.semi.security.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Setter
@Getter
@Entity
@Table(name = "users")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Column(unique = true)
    @Size(min = 4, message = "username length must be more than 4 characters")
    @NotNull
    private String username;

    @Size(min = 5, message = "password length must be more than 5 characters")
    @NotNull
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(targetEntity = Task.class)
    private List<Task> task;
}
