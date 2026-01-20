package com.project.workflow_approval_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workflow_approval_management_system.model.RequestType;


public interface RequestTypeRepo extends JpaRepository<RequestType, Integer> {
	public RequestType findByTypeOfReq(String type_of_req);
}
