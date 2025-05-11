package com.dtp.project_management_system.service;

import com.dtp.project_management_system.dto.TaskData;

public interface TaskService {
    TaskData getTaskData(Long projectId);
}
