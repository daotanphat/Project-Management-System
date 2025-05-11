package com.dtp.project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskData {
    private int totalTasks;
    private int completedTasks;
    private int inProgressTasks;
    private int todoTasks;
    private int taskCompletionRate;
    private int averageVelocity;
    private int taskRolloverRate;
}
