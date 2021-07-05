package com.group.approval.dto;

public class ApprovalStatus {
	private String apStatus_type;

	public ApprovalStatus() {
	}

	public ApprovalStatus(String apStatus_type) {
		this.apStatus_type = apStatus_type;
	}


	public String getApStatus_type() {
		return apStatus_type;
	}

	public void setApStatus_type(String apStatus_type) {
		this.apStatus_type = apStatus_type;
	}

	@Override
	public String toString() {
		return "ApprovalStatus [ap_type=" + apStatus_type + "]";
	}
	
}
