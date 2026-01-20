package com.project.workflow_approval_management_system.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.Repository.RequestRepo;
import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.RequestType;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.Workflow;

@Service
public class RequestWorkflowService {
	@Autowired
	RequestRepo requestRepo;
	
	public List<RequestDTO> managerRequest() {
		List<RequestDTO> requests = new ArrayList<>();
		for(Request request: requestRepo.findAll()) {
			List<Workflow> workflowCount = request.getWorkflow();
			if(workflowCount.size() ==1 && request.getUser() != null) {
				User user = request.getUser();
				RequestType type = request.getReqType();
				RequestDTO dto = new RequestDTO(request.getId(),request.getRequestTitle(),
						request.getRequestDesc(),user.getName(),user.getUsername(),
						type.getTypeOfReq());
				requests.add(dto);
			}
		}
		return requests;
	}
	public List<RequestDTO> hrRequest() {
		List<RequestDTO> requests = new ArrayList<>();
		for(Request request: requestRepo.findAll()) {
			List<Workflow> workflowCount = request.getWorkflow();
			RequestType reqType = request.getReqType();
			
			if(workflowCount.size() ==2 && reqType.getTypeOfReq().equals("Employee Requests")) {
				for(Workflow workflow: workflowCount) {
					if(workflow.getStep()==2 && workflow.isPending()) {
						User user = request.getUser();
						RequestType type = request.getReqType();
						RequestDTO dto = new RequestDTO(request.getId(),request.getRequestTitle(),
								request.getRequestDesc(),user.getName(),user.getUsername(),
								type.getTypeOfReq());
						requests.add(dto);
					}
				}
			}
		}
		return requests;
	}
	public List<RequestDTO> financeRequest() {
		List<RequestDTO> requests = new ArrayList<>();
		for(Request request: requestRepo.findAll()) {
			List<Workflow> workflowCount = request.getWorkflow();
			RequestType reqType = request.getReqType();
			if(workflowCount.size() ==2 && reqType.getTypeOfReq().equals("Finance Requests")) {
				for(Workflow workflow: workflowCount) {
					if(workflow.getStep()==2 && workflow.isPending()) {
						User user = request.getUser();
						RequestType type = request.getReqType();
						RequestDTO dto = new RequestDTO(request.getId(),request.getRequestTitle(),
								request.getRequestDesc(),user.getName(),user.getUsername(),
								type.getTypeOfReq());
						requests.add(dto);
					}
				}	
			}
		}
		return requests;
	}
	public List<RequestDTO> adminRequest() {
		List<RequestDTO> requests = new ArrayList<>();
		for(Request request: requestRepo.findAll()) {
			List<Workflow> workflowCount = request.getWorkflow();
			RequestType reqType = request.getReqType();
			if(workflowCount.size() ==2 && 
					!reqType.getTypeOfReq().equals("Finance Requests") && 
					!reqType.getTypeOfReq().equals("Employee Requests")) {
				for(Workflow workflow: workflowCount) {
					if(workflow.getStep()==2 && workflow.isPending()) {
						User user = request.getUser();
						RequestType type = request.getReqType();
						RequestDTO dto = new RequestDTO(request.getId(),request.getRequestTitle(),
								request.getRequestDesc(),user.getName(),user.getUsername(),
								type.getTypeOfReq());
						requests.add(dto);
					}
				}
			}
		}
		return requests;
	}
}
