package com.group.calendar.dto;



public class ScheduleType {
	   public String skd_type;

	public ScheduleType(String skd_type) {
		super();
		this.skd_type = skd_type;
	}

	

	public ScheduleType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSkd_type() {
		return skd_type;
	}

	public void setSkd_type(String skd_type) {
		this.skd_type = skd_type;
	}
	@Override
	   public String toString() {
	      return skd_type;
	   }
	   
}