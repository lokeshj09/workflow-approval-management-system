package com.project.workflow_approval_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Approval {
	@Id
	@GeneratedValue
	private Integer id;
	private boolean isApproved;
	private boolean isRejected;
	private String handledBy;
	
	@OneToOne(mappedBy = "approval")
	private Request request;
}
