package com.project.workflow_approval_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workflow_approval_management_system.model.Approval;

public interface ApprovalRepo extends JpaRepository<Approval, Integer>{

}
