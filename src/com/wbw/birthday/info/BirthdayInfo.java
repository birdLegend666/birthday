package com.wbw.birthday.info;

import java.util.ArrayList;
import java.util.List;


public class BirthdayInfo {
	private String id;
	private String name;   //����
	private String remark;  //��ע
	private int kind;   //����������
	private int year;   //��
	private int month;  //��+1
	private int day;    //��
	private String timeofday;  //hh:MM:ss
	private int alarmkind;   //��ʾ��ʽ
	
	public static List<BirthdayInfo> binfo_list = new ArrayList<BirthdayInfo>();
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getTimeofday() {
		return timeofday;
	}
	public void setTimeofday(String timeofday) {
		this.timeofday = timeofday;
	}
	public int getAlarmkind() {
		return alarmkind;
	}
	public void setAlarmkind(int alarmkind) {
		this.alarmkind = alarmkind;
	}
	
}
