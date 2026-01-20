package com.project.workflow_approval_management_system.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class RequestType {
	@Id
	@GeneratedValue
	private Integer id;
	private String typeOfReq;
	
	@OneToMany(mappedBy = "reqType")
	@JsonIgnoreProperties("reqType")
	private List<Request> request;
}
