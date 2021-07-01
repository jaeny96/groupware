package com.group.employee.dto;

public class Position {
	public int position_id;
	public String position_title;
	
	public Position() {
	}

	public Position(int position_id, String position_title) {
		super();
		this.position_id = position_id;
		this.position_title = position_title;
	}

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public String getPosition_title() {
		return position_title;
	}

	public void setPosition_title(String position_title) {
		this.position_title = position_title;
	}

	@Override
	public String toString() {
		return "Position [position_id=" + position_id + ", position_title=" + position_title + "]";
	}
	
	
	
	
	
}
