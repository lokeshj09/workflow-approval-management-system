package com.project.workflow_approval_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workflow_approval_management_system.model.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Integer>{
	public UserRole findByType(String type);
}
