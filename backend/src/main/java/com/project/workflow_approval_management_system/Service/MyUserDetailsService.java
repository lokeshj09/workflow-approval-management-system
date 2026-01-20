package com.project.workflow_approval_management_system.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.workflow_approval_management_system.Repository.UserRepo;
import com.project.workflow_approval_management_system.model.User;
import com.project.workflow_approval_management_system.model.UserPrincipal;
import com.project.workflow_approval_management_system.model.UserRole;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		UserRole userRole = user.getUserRole();
		if(!user.getUsername().equals(username)) {
			throw new UsernameNotFoundException("Error finding user:"+username);
		}
		
		return new UserPrincipal(user,userRole.getType());
	}
}
