package com.dtp.project_management_system.controller;

import com.dtp.project_management_system.dto.ApiResponse;
import com.dtp.project_management_system.dto.ProjectHealthRequest;
import com.dtp.project_management_system.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/health/{projectId}")
    public ResponseEntity<ApiResponse<ProjectHealthRequest>> getProjectHealth(@PathVariable Long projectId) {
        ProjectHealthRequest projectHealthRequest = projectService.getProjectHealthRequest(projectId);

        ApiResponse<ProjectHealthRequest> response = new ApiResponse<>(
                true,
                "Project health data retrieved successfully",
                projectHealthRequest
        );
        return ResponseEntity.ok(response);
    }
}
