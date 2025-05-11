package com.dtp.project_management_system.repository;

import com.dtp.project_management_system.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);

    @Query("SELECT COUNT(DISTINCT t.assignedUser) FROM Task t " +
            " WHERE t.project.id = :projectId" +
            " GROUP BY t.project.id")
    int numberMembersInProject(@Param("projectId") Long projectId);
}
