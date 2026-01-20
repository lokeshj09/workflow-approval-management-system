package com.project.workflow_approval_management_system.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.workflow_approval_management_system.DTO.DetailsDTO;
import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.Repository.ApprovalRepo;
import com.project.workflow_approval_management_system.Repository.RequestRepo;
import com.project.workflow_approval_management_system.Repository.WorkflowRepo;
import com.project.workflow_approval_management_system.model.Approval;
import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.Workflow;

@Service
public class RequestApprovalService {
	@Autowired
	ApprovalRepo approvalRepo ;
	@Autowired
	RequestRepo requestRepo;
	@Autowired
	WorkflowRepo workflowRepo;
	
	public String request_approval(RequestDTO request,boolean isAccepted,DetailsDTO user) {
		Workflow workflow = new Workflow();
		Request req = requestRepo.findById(request.id()).get();
		List<Workflow> workflowCount = req.getWorkflow();
		if(workflowCount.size()==2) {
			Approval approval = new Approval();
			approval.setApproved(isAccepted);
			approval.setRejected(!isAccepted);
			approval.setHandledBy(user.username());
			approvalRepo.save(approval);
			req.setApproval(approval);
			requestRepo.save(req);
			workflow.setPending(false);
		} else {
			workflow.setPending(isAccepted);
		}
		workflow.setRequest(req);
		workflow.setStep(workflowCount.size()+1);
		workflowRepo.save(workflow);
		return "Success";
	}
}
