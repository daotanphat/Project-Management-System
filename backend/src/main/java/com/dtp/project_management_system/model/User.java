package com.dtp.project_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String role;

    @OneToMany(mappedBy = "assignedUser")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "manager")
    private List<Project> managedProjects;
}
