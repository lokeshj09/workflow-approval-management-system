package com.project.workflow_approval_management_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.Service.RequestWorkflowService;

@RestController
public class RequestWorkflowController {
	@Autowired
	private RequestWorkflowService workflowService;
	
	@GetMapping("/manager/requests")
	public List<RequestDTO> managerRequest() {
		return workflowService.managerRequest();
	}
	
	@GetMapping("/h.r./requests")
	public List<RequestDTO> hrRequest() {
		return workflowService.hrRequest();
	}
	@GetMapping("/finance/requests")
	public List<RequestDTO> financeRequest() {
		return workflowService.financeRequest();
	}
	@GetMapping("/admin/requests")
	public List<RequestDTO> adminRequest() {
		return workflowService.adminRequest();
	}
}
