package com.dtp.project_management_system.service.implement;

import com.dtp.project_management_system.dto.ProjectDTO;
import com.dtp.project_management_system.dto.ProjectHealthData;
import com.dtp.project_management_system.dto.ProjectHealthRequest;
import com.dtp.project_management_system.mapper.ProjectMapper;
import com.dtp.project_management_system.model.Project;
import com.dtp.project_management_system.prompt.AIPrompt;
import com.dtp.project_management_system.repository.ProjectRepository;
import com.dtp.project_management_system.repository.UserRepository;
import com.dtp.project_management_system.service.ProjectService;
import com.dtp.project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

    @Override
    public ProjectHealthData getProjectHealthData(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        ProjectDTO projectDTO = ProjectMapper.INSTANCE.toDto(project);
        int teamSize = userRepository.findByProjectId(projectId).size();

        ProjectHealthData projectHealthData = new ProjectHealthData();
        projectHealthData.setName(projectDTO.getName());
        projectHealthData.setStartDate(projectDTO.getStartDate());
        projectHealthData.setEndDate(projectDTO.getEndDate());
        projectHealthData.setTeamSize(teamSize);
        projectHealthData.setTaskData(taskService.getTaskData(projectId));
        return projectHealthData;
    }

    @Override
    public ProjectHealthRequest getProjectHealthRequest(Long projectId) {
        ProjectHealthData projectHealthData = getProjectHealthData(projectId);
        ProjectHealthRequest projectHealthRequest = new ProjectHealthRequest();
        projectHealthRequest.setPrompt(AIPrompt.PROJECT_HEALTH_PROMPT);
        projectHealthRequest.setProjectHealthData(projectHealthData);
        return projectHealthRequest;
    }
}
