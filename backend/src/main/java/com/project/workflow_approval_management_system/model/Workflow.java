package com.project.workflow_approval_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workflow {
	@Id
	@GeneratedValue
	private Integer id;
	private boolean pending;
	private int step;
	
	@ManyToOne
	@JoinColumn(name = "req_id")
	@JsonIgnoreProperties("workflow")
	private Request request;
}
