package com.project.workflow_approval_management_system.Service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.workflow_approval_management_system.DTO.DetailsDTO;
import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.DTO.UserRequestDTO;
import com.project.workflow_approval_management_system.Repository.RequestRepo;
import com.project.workflow_approval_management_system.Repository.RequestTypeRepo;
import com.project.workflow_approval_management_system.Repository.UserRepo;
import com.project.workflow_approval_management_system.Repository.WorkflowRepo;
import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.RequestType;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.UserRole;
import com.project.workflow_approval_management_system.model.Workflow;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {
	@InjectMocks
	private RequestService requestService;
	@Mock
	private JWTService jwt;
	@Mock
	private UserRepo userRepo;
	@Mock
	private RequestRepo reqRepo;
	@Mock
	private WorkflowRepo workflowRepo;
	@Mock
	private RequestTypeRepo requestTypeRepo;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetDetails() {
		String token = "dummyToken";
        String username = "john123";

        UserRole role = new UserRole();
        role.setType("ADMIN");

        User user = new User();
        user.setName("John Doe");
        user.setUsername(username);
        user.setEmail("john@gmail.com");
        user.setUserRole(role);

        Mockito.when(jwt.extractUsername(token)).thenReturn(username);
        Mockito.when(userRepo.findByUsername(username)).thenReturn(user);

        // Act
        DetailsDTO result = requestService.getDetails(token);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals("john123", result.username());
        assertEquals("john@gmail.com", result.email());
        assertEquals("ADMIN", result.role());

        Mockito.verify(jwt).extractUsername(token);
        Mockito.verify(userRepo).findByUsername(username);
	}

	@Test
	void testGenReq() {
		Date date = new Date();
		
        String username = "john123";

        UserRole role = new UserRole();
        role.setType("ADMIN");
		
		User user = new User();
        user.setName("John Doe");
        user.setUsername(username);
        user.setEmail("john@gmail.com");
        user.setUserRole(role);
        
        RequestType reqType = new RequestType();
        reqType.setTypeOfReq("reqType");
		
		RequestDTO req = new RequestDTO(1,"Title","Desc",user.getName(),user.getUsername(),"reqType");
		
		Request request = new Request();
		request.setRequestTitle(req.reqTitle());
		request.setRequestDesc(req.reqDesc());
		request.setGenerateAt(date);
		request.setUser(user);
		request.setReqType(reqType);
		
		Mockito.when(userRepo.findByUsername(username)).thenReturn(user);
		Mockito.when(requestTypeRepo.findByTypeOfReq(req.reqType())).thenReturn(reqType);
		Mockito.when(reqRepo.save(Mockito.any(Request.class))).thenAnswer(inv -> inv.getArgument(0));
		Mockito.when(workflowRepo.save(Mockito.any(Workflow.class))).thenAnswer(inv -> inv.getArgument(0));
		
		Request answerRequest = requestService.genReq(req);
		
		assertNotNull(answerRequest);
		assertEquals(request.getRequestTitle(),answerRequest.getRequestTitle());
		assertEquals(request.getRequestDesc(),answerRequest.getRequestDesc());
		assertEquals(request.getUser().getUsername(),answerRequest.getUser().getUsername());
		assertNotNull(answerRequest.getWorkflow().size());
		assertEquals(request.getReqType().getTypeOfReq(),answerRequest.getReqType().getTypeOfReq());
		
		Mockito.verify(requestTypeRepo).findByTypeOfReq(req.reqType());
		Mockito.verify(userRepo).findByUsername(username);
		Mockito.verify(reqRepo).save(Mockito.any(Request.class));
		Mockito.verify(workflowRepo).save(Mockito.any(Workflow.class));
	}
		
	@Test
	void testShowReq() {
		String username = "john123";

        UserRole role = new UserRole();
        role.setType("ADMIN");
		
		User user = new User();
        user.setName("John Doe");
        user.setUsername(username);
        user.setEmail("john@gmail.com");
        user.setUserRole(role);
        
    	RequestDTO req = new RequestDTO(1,"Title","Desc",user.getName(),user.getUsername(),"reqType");

		RequestType reqType = new RequestType();
		reqType.setTypeOfReq("reqType");
		
		List<Request> requests = new ArrayList<>();
		Request request = new Request();
		request.setId(1);
		request.setRequestTitle(req.reqTitle());
		request.setRequestDesc(req.reqDesc());
		request.setGenerateAt(new Date());
		request.setUser(user);
		request.setReqType(reqType);
		requests.add(request);
		
		Mockito.when( userRepo.findByUsername(username)).thenReturn(user);
		Mockito.when(reqRepo.findByUser(user)).thenReturn(requests);
		
		List<UserRequestDTO> userRequests = requestService.showReq(username);
		
		assertNotNull(userRequests);
		assertEquals(1, userRequests.size());
		assertFalse(userRequests.isEmpty());
		
		UserRequestDTO userRequest = userRequests.get(0);
		assertEquals(request.getRequestDesc(), userRequest.reqDesc());
		assertEquals(request.getRequestTitle(), userRequest.reqTitle());
		assertEquals(request.getReqType().getTypeOfReq(), userRequest.reqType());
	}

}
