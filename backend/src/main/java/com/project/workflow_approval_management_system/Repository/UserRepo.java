package com.project.workflow_approval_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workflow_approval_management_system.model.User;


public interface UserRepo extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
}
