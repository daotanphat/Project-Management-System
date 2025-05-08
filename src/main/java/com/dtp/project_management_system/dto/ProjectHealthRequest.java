package com.dtp.project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectHealthRequest {
    private String prompt;
    private ProjectHealthData projectHealthData;
}
