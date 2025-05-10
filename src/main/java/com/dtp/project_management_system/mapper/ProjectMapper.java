package com.dtp.project_management_system.mapper;

import com.dtp.project_management_system.dto.ProjectDTO;
import com.dtp.project_management_system.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "manager.fullName", target = "manager")
    ProjectDTO toDto(Project project);
}