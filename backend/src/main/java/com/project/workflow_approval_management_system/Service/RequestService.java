package com.project.workflow_approval_management_system.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.workflow_approval_management_system.DTO.DetailsDTO;
import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.DTO.UserRequestDTO;
import com.project.workflow_approval_management_system.Repository.RequestRepo;
import com.project.workflow_approval_management_system.Repository.RequestTypeRepo;
import com.project.workflow_approval_management_system.Repository.UserRepo;
import com.project.workflow_approval_management_system.Repository.WorkflowRepo;
import com.project.workflow_approval_management_system.model.Approval;
import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.RequestType;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.UserRole;
import com.project.workflow_approval_management_system.model.Workflow;

@Service
public class RequestService {
	@Autowired
	private JWTService jwt;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RequestRepo reqRepo;
	@Autowired
	private RequestTypeRepo requestTypeRepo;
	@Autowired
	private WorkflowRepo workflowRepo;
	
	private User user;
	private UserRole userRole;
	
	public DetailsDTO getDetails(String token) {
		String username = jwt.extractUsername(token);
		user = userRepo.findByUsername(username);
		userRole = user.getUserRole();
		DetailsDTO details = new DetailsDTO(user.getName(),user.getUsername(),user.getEmail(),userRole.getType());
		return details;
	}

	public Request genReq(RequestDTO req) {
		Date date = new Date();
		RequestType reqType = requestTypeRepo.findByTypeOfReq(req.reqType());
		User user = userRepo.findByUsername(req.username());
		Request request = new Request();
		request.setRequestTitle(req.reqTitle());
		request.setRequestDesc(req.reqDesc());
		request.setGenerateAt(date);
		request.setUser(user);
		request.setReqType(reqType);
		List<Workflow> workflows = new ArrayList<>();
		Workflow workflow = new Workflow();
		workflow.setPending(true);
		workflow.setRequest(request);
		workflow.setStep(1); 
		workflows.add(workflow);
		request.setWorkflow(workflows);
		reqRepo.save(request);
		workflowRepo.save(workflow);
		return request;
	}

	public List<UserRequestDTO> showReq(String username) {
		User user = userRepo.findByUsername(username);
		List<Request> request = reqRepo.findByUser(user);
		List<UserRequestDTO> requestDTO = new ArrayList<>();
		for(Request req : request) {
			RequestType reqType = req.getReqType();
			String Status = "";
			Approval approval = req.getApproval();
			if(approval!=null) {
				if(approval.isApproved()) {
					Status = "Approved";
				}
				else
				if(approval.isRejected()){
					Status = "Rejected";
				}
			} else {
				Status = "Pending";
			}
			
			UserRequestDTO reqDTODto = new UserRequestDTO(req.getId(),req.getRequestTitle(), req.getRequestDesc(), user.getName(), user.getUsername(), reqType.getTypeOfReq(),Status);
			requestDTO.add(reqDTODto);
		}
		return requestDTO;
	}
}
