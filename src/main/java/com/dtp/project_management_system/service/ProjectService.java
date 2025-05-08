package com.dtp.project_management_system.service;

import com.dtp.project_management_system.dto.ProjectHealthData;
import com.dtp.project_management_system.dto.ProjectHealthRequest;

public interface ProjectService {
    ProjectHealthData getProjectHealthData(Long projectId);

    ProjectHealthRequest getProjectHealthRequest(Long projectId);
}
