package com.group.approval.dto;

public class ApprovalStatus {
	public String ap_type;

	public ApprovalStatus() {
	}

	public ApprovalStatus(String ap_type) {
		this.ap_type = ap_type;
	}

	public String getAp_type() {
		return ap_type;
	}

	public void setAp_type(String ap_type) {
		this.ap_type = ap_type;
	}

	@Override
	public String toString() {
		return "ApprovalStatus [ap_type=" + ap_type + "]";
	}
	
	
}
