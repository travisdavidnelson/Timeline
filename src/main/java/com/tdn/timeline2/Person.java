package com.tdn.timeline2;

import java.util.ArrayList;
import java.util.List;

public class Person extends TimelineEvent {
	private String importance = null;
	private List<TimelineEvent> titles = null;
	
	public Person() {
		this.titles = new ArrayList<TimelineEvent>();
	}

	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
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
	
	
}
