package com.group.calendar.dto;

public class ScheduleType {
	public String skd_type;

	//++지수 추가
	public ScheduleType() {
	}

	public ScheduleType(String skd_type) {
		super();
		this.skd_type = skd_type;
	}

	public String getSkd_type() {
		return skd_type;
	}

	public void setSkd_type(String skd_type) {
		this.skd_type = skd_type;
	}

	@Override
	public String toString() {
		return "ScheduleType [skd_type=" + skd_type + "]";
	}
	//++
}
