package com.tdn.timeline2;

import java.util.ArrayList;
import java.util.List;

public class Person extends TimelineEvent {
	public static String TRANSFORMATIONAL = "transformational";
	public static String MAJOR = "major";
	public static String SEMI_MAJOR = "semimajor";
	public static String MINOR = "minor";

	private String importance = null;
	private String fate = null;
	private List<TimelineEvent> titles = null;
	
	public Person() {
		this.titles = new ArrayList<TimelineEvent>();
		this.importance = MINOR;
	}

	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getFate() {
		return fate;
	}
	public void setFate(String fate) {
		this.fate = fate;
	}

	public List<TimelineEvent> getTitles() {
		return titles;
	}
	public void setTitles(List<TimelineEvent> titles) {
		this.titles = titles;
	}
	public void addTitle(TimelineEvent title) {
		this.titles.add(title);
	}
	
	public int getHeight() {
		if (MINOR.equals(importance)) {
			return 6;
		}
		else if (SEMI_MAJOR.equals(importance)) {
			return 10;
		}
		else if (MAJOR.equals(importance)) {
			return 15;
		}
		else {
			return 20;
		}
	}
	

}
