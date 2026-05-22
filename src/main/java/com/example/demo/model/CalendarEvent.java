package com.example.demo.model;

public class CalendarEvent {
	private String title;
	private String start;

	public CalendarEvent() {
	}

	public CalendarEvent(String title, String start) {
		this.title = title;
		this.start = start;
	}

	public String getTitle() {
		return title;
	}

	public String getStart() {
		return start;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStart(String start) {
		this.start = start;
	}
}
