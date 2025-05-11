package com.dtp.project_management_system.repository;

import com.dtp.project_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.managedProjects p WHERE p.id = :projectId")
    List<User> findByProjectId(@Param("projectId") Long projectId);
}
