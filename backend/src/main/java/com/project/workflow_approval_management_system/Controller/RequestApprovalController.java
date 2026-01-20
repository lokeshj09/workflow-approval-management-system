package com.project.workflow_approval_management_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.workflow_approval_management_system.DTO.ApprovalDTO;
import com.project.workflow_approval_management_system.Service.RequestApprovalService;

@RestController
public class RequestApprovalController {
	@Autowired
	private RequestApprovalService approvalService;
	
	@PostMapping("/request-approval")
	public void request_approval(@RequestBody ApprovalDTO appDTO) {
		approvalService.request_approval(appDTO.req(),appDTO.isAccepted(),appDTO.user());
	}
}
