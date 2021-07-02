package com.group.calendar.dto;

public class ScheduleType {
	   public String skd_type;

	public String getSkd_type() {
		return skd_type;
	}

	public void setSkd_type(String skd_type) {
		this.skd_type = skd_type;
	}

	public ScheduleType() {
		
	}
	
	public ScheduleType(String skd_type) {
		
		this.skd_type = skd_type;
	}

	@Override
	public String toString() {
		return ""+skd_type+"";
	}	   
	
	   
}
