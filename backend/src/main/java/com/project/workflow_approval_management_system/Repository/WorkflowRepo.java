package com.project.workflow_approval_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workflow_approval_management_system.model.Workflow;

public interface WorkflowRepo extends JpaRepository<Workflow, Integer>{

}
