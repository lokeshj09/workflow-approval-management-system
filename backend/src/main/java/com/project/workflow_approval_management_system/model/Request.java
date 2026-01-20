package com.project.workflow_approval_management_system.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String requestTitle;
	private String requestDesc;
	private Date generateAt;
	
	@ManyToOne
	@JsonIgnoreProperties("request")
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JsonIgnoreProperties("request")
	@JoinColumn(name = "req_type_id")
	private RequestType reqType;
	
	@OneToMany(mappedBy = "request")
	@JsonIgnoreProperties("request")
	private List<Workflow> workflow;
	
	@OneToOne
	private Approval approval;
}
