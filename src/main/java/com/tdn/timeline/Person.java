package com.tdn.timeline;

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
	private List<TimelineEvent> foregroundEvents = null;
	
	public Person() {
		this.titles = new ArrayList<TimelineEvent>();
		this.foregroundEvents = new ArrayList<TimelineEvent>();
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

	public List<TimelineEvent> getForegroundEvents() {
		return foregroundEvents;
	}
	public void setForegroundEvents(List<TimelineEvent> foregroundEvents) {
		this.foregroundEvents = foregroundEvents;
	}
	public void addForegroundEvent(TimelineEvent event) {
		this.foregroundEvents.add(event);
	}

}
