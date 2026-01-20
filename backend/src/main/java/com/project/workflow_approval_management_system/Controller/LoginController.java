package com.project.workflow_approval_management_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.workflow_approval_management_system.DTO.LoginDTO;
import com.project.workflow_approval_management_system.Service.LoginService;
import com.project.workflow_approval_management_system.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public String loginUser(@RequestBody LoginDTO user,HttpServletResponse res) {
		return loginService.login(user,res);
	}
	
	@PostMapping("/refresh/token")
	public String refreshToken(HttpServletRequest req,HttpServletResponse res) {
		return loginService.refreshToken(req,res);
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return loginService.register(user);
	}
	
}
