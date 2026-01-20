package com.project.workflow_approval_management_system.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	private String username;
	private String email;
	private String password;
	private boolean active;
	@CreationTimestamp
	@Column(updatable = false)
	private Date createdAt;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties("user")
	private List<Request> request;
	@ManyToOne
	@JsonIgnoreProperties("user")
	@JoinColumn(name = "user_role_id")
	private UserRole userRole;
}
