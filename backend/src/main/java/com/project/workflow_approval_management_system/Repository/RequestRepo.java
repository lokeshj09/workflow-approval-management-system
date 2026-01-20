package com.project.workflow_approval_management_system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.User;

public interface RequestRepo extends JpaRepository<Request, Integer>{
	public List<Request> findByUser(User user);
}
