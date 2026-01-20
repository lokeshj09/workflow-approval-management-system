package com.project.workflow_approval_management_system.Service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.Repository.RequestRepo;
import com.project.workflow_approval_management_system.model.Request;
import com.project.workflow_approval_management_system.model.RequestType;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.Workflow;

@ExtendWith(MockitoExtension.class)
class RequestWorkflowServiceTest {

    @InjectMocks
    private RequestWorkflowService service;

    @Mock
    private RequestRepo requestRepo;

    private User buildUser() {
        User user = new User();
        user.setName("John Doe");
        user.setUsername("john123");
        return user;
    }

    private RequestType buildType(String type) {
        RequestType rt = new RequestType();
        rt.setTypeOfReq(type);
        return rt;
    }

    private Workflow workflow(int step, boolean pending) {
        Workflow wf = new Workflow();
        wf.setStep(step);
        wf.setPending(pending);
        return wf;
    }

    private Request buildRequest(
            String type,
            int workflowCount,
            boolean step2Pending,
            boolean withUser
    ) {
        Request request = new Request();
        request.setId(1);
        request.setRequestTitle("Title");
        request.setRequestDesc("Desc");
        request.setReqType(buildType(type));
        request.setUser(withUser ? buildUser() : null);

        List<Workflow> workflows = new ArrayList<>();
        if (workflowCount == 1) {
            workflows.add(workflow(1, true));
        } else if (workflowCount == 2) {
            workflows.add(workflow(1, false));
            workflows.add(workflow(2, step2Pending));
        }

        request.setWorkflow(workflows);
        return request;
    }

    @Test
    void testManagerRequest() {
        Request req = buildRequest("Any", 1, true, true);
        Mockito.when(requestRepo.findAll()).thenReturn(List.of(req));

        List<RequestDTO> result = service.managerRequest();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("john123", result.get(0).username());
    }

    @Test
    void testHrRequest() {
        Request req = buildRequest("Employee Requests", 2, true, true);
        Mockito.when(requestRepo.findAll()).thenReturn(List.of(req));

        List<RequestDTO> result = service.hrRequest();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Employee Requests", result.get(0).reqType());
    }

    @Test
    void testFinanceRequest() {
        Request req = buildRequest("Finance Requests", 2, true, true);
        Mockito.when(requestRepo.findAll()).thenReturn(List.of(req));

        List<RequestDTO> result = service.financeRequest();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Finance Requests", result.get(0).reqType());
    }

    @Test
    void testAdminRequest() {
        Request req = buildRequest("IT Requests", 2, true, true);
        Mockito.when(requestRepo.findAll()).thenReturn(List.of(req));

        List<RequestDTO> result = service.adminRequest();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IT Requests", result.get(0).reqType());
    }

    @Test
    void testNoMatchingRequests() {
        Request req = buildRequest("Employee Requests", 2, false, true);
        Mockito.when(requestRepo.findAll()).thenReturn(List.of(req));

        List<RequestDTO> result = service.hrRequest();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
