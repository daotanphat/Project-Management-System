package com.dtp.project_management_system.controller;

import com.dtp.project_management_system.dto.ApiResponse;
import com.dtp.project_management_system.dto.GeminiRequest;
import com.dtp.project_management_system.dto.ProjectHealthRequest;
import com.dtp.project_management_system.prompt.AIPrompt;
import com.dtp.project_management_system.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH},
        allowCredentials = "true")
public class ProjectController {
    private final ProjectService projectService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private static final String GEMINI_URL_BASE
            = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public ProjectController(ProjectService projectService, RestTemplate restTemplate,
                             ObjectMapper objectMapper, @Value("${gemini.api.key}") String apiKey) {
        this.projectService = projectService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
    }

    @GetMapping("/health/{projectId}")
    public ResponseEntity<ApiResponse<String>> getProjectHealth(@PathVariable Long projectId)
            throws JsonProcessingException {
        ProjectHealthRequest projectHealthRequest
                = projectService.getProjectHealthRequest(projectId, AIPrompt.PROJECT_HEALTH_PROMPT);

        String geminiUrl = GEMINI_URL_BASE + apiKey;
        GeminiRequest geminiRequest = new GeminiRequest();
        geminiRequest.setContents(new GeminiRequest.Content[]{
                new GeminiRequest.Content(new GeminiRequest.Part[]{
                        new GeminiRequest.Part(projectHealthRequest.getPrompt()),
                        new GeminiRequest.Part(projectHealthRequest.getPrompt() + "\n"
                                + objectMapper.writeValueAsString(projectHealthRequest.getProjectHealthData()))
                })
        });

        String geminiResponse = restTemplate.postForObject(
                geminiUrl,
                geminiRequest,
                String.class
        );

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Response from Gemini API retrieved successfully",
                geminiResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<ApiResponse<String>> chatProjectStatus(@PathVariable Long projectId,
                                                                 @RequestParam String prompt)
            throws JsonProcessingException {
        ProjectHealthRequest projectHealthRequest
                = projectService.getProjectHealthRequest(projectId, prompt);

        String geminiUrl = GEMINI_URL_BASE + apiKey;
        GeminiRequest geminiRequest = new GeminiRequest();
        geminiRequest.setContents(new GeminiRequest.Content[]{
                new GeminiRequest.Content(new GeminiRequest.Part[]{
                        new GeminiRequest.Part(projectHealthRequest.getPrompt()),
                        new GeminiRequest.Part(projectHealthRequest.getPrompt() + "\n"
                                + objectMapper.writeValueAsString(projectHealthRequest.getProjectHealthData()))
                })
        });

        String geminiResponse = restTemplate.postForObject(
                geminiUrl,
                geminiRequest,
                String.class
        );

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Response from Gemini API retrieved successfully",
                geminiResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/information/{projectId}")
    public ResponseEntity<ProjectHealthRequest> informationProject(@PathVariable Long projectId) {
        ProjectHealthRequest projectHealthRequest
                = projectService.getProjectHealthRequest(projectId, AIPrompt.PROJECT_HEALTH_PROMPT);
        return ResponseEntity.ok(projectHealthRequest);
    }
}
