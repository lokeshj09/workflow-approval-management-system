package com.project.workflow_approval_management_system.Service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.workflow_approval_management_system.DTO.DetailsDTO;
import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.Repository.ApprovalRepo;
import com.project.workflow_approval_management_system.Repository.RequestRepo;
import com.project.workflow_approval_management_system.Repository.WorkflowRepo;
import com.project.workflow_approval_management_system.model.Approval;
import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.RequestType;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.UserRole;
import com.project.workflow_approval_management_system.model.Workflow;

@ExtendWith(MockitoExtension.class)
class RequestApprovalServiceTest {
	@InjectMocks
	private RequestApprovalService approvalService;
	@Mock
	private ApprovalRepo approvalRepo;
	@Mock
	private RequestRepo requestRepo;
	@Mock
	private WorkflowRepo workflowRepo;

	@Test
	void testRequest_isApproved() {
        String username = "john123";

        UserRole role = new UserRole();
        role.setType("ADMIN");

        User user = new User();
        user.setName("John Doe");
        user.setUsername(username);
        user.setEmail("john@gmail.com");
        user.setUserRole(role);
        
        DetailsDTO userDTO = new DetailsDTO(user.getName(),user.getUsername(),user.getEmail(),user.getPassword());
		
		RequestType reqType = new RequestType();
		reqType.setTypeOfReq("reqType");
		RequestDTO requestDTO = new RequestDTO(1,"Title","Desc",user.getName(),user.getUsername(),reqType.getTypeOfReq());
		Workflow wf1 = new Workflow();
		wf1.setPending(true);
		wf1.setStep(1);
		Workflow wf2 = new Workflow();
		wf2.setPending(true);
		wf2.setStep(2);
		Request request = new Request();
		request.setId(requestDTO.id());
		request.setRequestTitle(requestDTO.reqTitle());
		request.setRequestDesc(requestDTO.reqDesc());
		request.setUser(user);
		request.setWorkflow(List.of(wf1,wf2));
		request.setReqType(reqType);
		
		Mockito.when(requestRepo.findById(requestDTO.id())).thenReturn(Optional.of(request));
		Mockito.when(approvalRepo.save(Mockito.any(Approval.class))).thenAnswer(inv ->inv.getArgument(0));
		Mockito.when(requestRepo.save(Mockito.any(Request.class))).thenReturn(request);
		Mockito.when(workflowRepo.save(Mockito.any(Workflow.class))).thenAnswer(inv ->inv.getArgument(0));
		
		String result = approvalService.request_approval(requestDTO, true, userDTO);
		
		assertEquals("Success", result);
		
		Mockito.verify(approvalRepo, Mockito.times(1)).save(Mockito.any(Approval.class));
	    Mockito.verify(requestRepo, Mockito.times(1)).save(request);
	    Mockito.verify(workflowRepo, Mockito.times(1)).save(Mockito.any(Workflow.class));
	}

}
