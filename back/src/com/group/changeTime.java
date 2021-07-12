package com.group;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class changeTime {
	public static String modifyTime(Timestamp time) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeStr = sd.format(new Date(time.getTime()));

		return timeStr;
	}
}