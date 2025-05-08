package com.dtp.project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectHealthData {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int teamSize;
    private TaskData taskData;
}
