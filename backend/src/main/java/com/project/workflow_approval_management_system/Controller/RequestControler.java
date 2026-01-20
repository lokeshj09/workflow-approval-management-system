package com.project.workflow_approval_management_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.workflow_approval_management_system.DTO.DetailsDTO;
import com.project.workflow_approval_management_system.DTO.RequestDTO;
import com.project.workflow_approval_management_system.DTO.UserRequestDTO;
import com.project.workflow_approval_management_system.Service.RequestService;
import com.project.workflow_approval_management_system.model.Request;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class RequestControler {
	@Autowired
	private RequestService reqService;
	
	@GetMapping("/getDetails")
	public DetailsDTO getDetails(HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		return reqService.getDetails(token);
	}
	
	@PostMapping("/genRequest")
	public Request genRequest(@RequestBody RequestDTO req) {
		return reqService.genReq(req);
	}
	
	@GetMapping("/displayReq")
	public List<UserRequestDTO> showReq(@RequestParam String username) {
		return reqService.showReq(username);
	}
}
