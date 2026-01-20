package com.project.workflow_approval_management_system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.workflow_approval_management_system.DTO.LoginDTO;
import com.project.workflow_approval_management_system.Repository.UserRepo;
import com.project.workflow_approval_management_system.Repository.UserRoleDAO;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.UserPrincipal;
import com.project.workflow_approval_management_system.model.UserRole;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService {
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JWTService jwtService;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	public String login(LoginDTO user,HttpServletResponse res) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(),user.password()));
		String loginToken = jwtService.generateRefreshToken(user.username());
		ResponseCookie loginCookie = ResponseCookie.from("loginCookie",loginToken)
										.httpOnly(true)
										.maxAge(7*24*60*60)
										.path("/")
										.secure(false)
										.sameSite("Lax")
										.build();
		res.addHeader(HttpHeaders.SET_COOKIE,loginCookie.toString());
		if(auth.isAuthenticated()) {
			return jwtService.generateToken(user.username());
		}
		throw new RuntimeException("Invalid login");
	}
	
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		UserRole UR = user.getUserRole();
		UserRole UR2 = userRoleDAO.findByType(UR.getType());
		UR.setId(UR2.getId());
		user.setUserRole(UR);
		userRepo.save(user);
		return user;
	}

	public String refreshToken(HttpServletRequest req,HttpServletResponse res) {
		String loginToken = null ;
		if(req.getCookies()!=null) {
			for(Cookie cookie:req.getCookies()) {
				if("loginCookie".equals(cookie.getName())) {
					loginToken = cookie.getValue();
				}
			}
		}
		String username = jwtService.extractUsername(loginToken);
		UserPrincipal userPrincipal = (UserPrincipal) context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
		if(!jwtService.validateRefreshToken(loginToken, userPrincipal)) {
			throw new RuntimeException("Invalid refresh token");
		}
		String accessToken = jwtService.generateToken(username);
		return accessToken;
	}
}
