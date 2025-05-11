package com.dtp.project_management_system.service.implement;

import com.dtp.project_management_system.dto.TaskData;
import com.dtp.project_management_system.repository.SprintRepository;
import com.dtp.project_management_system.repository.TaskRepository;
import com.dtp.project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

    @Override
    public TaskData getTaskData(Long projectId) {
        int totalTasks = taskRepository.findByProjectId(projectId).size();
        int doneTasks = taskRepository.findByProjectId(projectId).stream()
                .filter(task -> task.getStatus().equals("DONE"))
                .toList()
                .size();
        int inProgressTasks = taskRepository.findByProjectId(projectId).stream()
                .filter(task -> task.getStatus().equals("IN_PROGRESS"))
                .toList()
                .size();
        int todoTasks = taskRepository.findByProjectId(projectId).stream()
                .filter(task -> task.getStatus().equals("TODO"))
                .toList()
                .size();
        int totalSprints = sprintRepository.findByProjectId(projectId).size();

        int taskCompletionRate = totalTasks > 0 ? (doneTasks * 100 / totalTasks) : 0;
        int averageVelocity = totalSprints > 0 ? (doneTasks * 100) / totalSprints : 0;
        int taskRolloverRate = totalTasks > 0 ? ((todoTasks + inProgressTasks) * 100) / totalTasks : 0;
        return new TaskData(totalTasks, doneTasks, inProgressTasks, todoTasks, taskCompletionRate, averageVelocity, taskRolloverRate);
    }
}
