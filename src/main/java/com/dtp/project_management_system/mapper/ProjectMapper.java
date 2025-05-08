package com.dtp.project_management_system.mapper;

import com.dtp.project_management_system.dto.ProjectDTO;
import com.dtp.project_management_system.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(source = "manager.fullName", target = "manager")
    ProjectDTO toDto(Project project);
}