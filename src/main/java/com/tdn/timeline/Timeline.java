package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends TimelineEvent {
	private String id = null;
	private String author = "Travis David Nelson";
	private TimelineConfig config = null;
	
	private List<TimelineEvent> backgroundEvents;
	private List<DynastyGroup> politicalDynastyGroups;
	private List<DynastyGroup> culturalDynastyGroups;
	
	public Timeline() {
		config = new TimelineConfig();
		
		backgroundEvents = new ArrayList<TimelineEvent>();
		politicalDynastyGroups = new ArrayList<DynastyGroup>();
		culturalDynastyGroups = new ArrayList<DynastyGroup>();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public TimelineConfig getConfig() {
		return config;
	}
	public void setConfig(TimelineConfig config) {
		this.config = config;
	}

	public List<TimelineEvent> getBackgroundEvents() {
		return backgroundEvents;
	}
	public void setBackgroundEvents(List<TimelineEvent> backgroundEvents) {
		this.backgroundEvents = backgroundEvents;
	}
	public void addBackgroundEvent(TimelineEvent backgroundEvent) {
		this.backgroundEvents.add(backgroundEvent);
	}

	public List<DynastyGroup> getPoliticalDynastyGroups() {
		return politicalDynastyGroups;
	}
	public void setPoliticalDynastyGroups(List<DynastyGroup> politicalDynastyGroups) {
		this.politicalDynastyGroups = politicalDynastyGroups;
	}
	public void addPoliticalDynastyGroup(DynastyGroup dynastyGroup) {
		this.politicalDynastyGroups.add(dynastyGroup);
	}

	public List<DynastyGroup> getCulturalDynastyGroups() {
		return culturalDynastyGroups;
	}
	public void setCulturalDynastyGroups(List<DynastyGroup> culturalDynastyGroups) {
		this.culturalDynastyGroups = culturalDynastyGroups;
	}
	public void addCulturalDynastyGroup(DynastyGroup dynastyGroup) {
		this.culturalDynastyGroups.add(dynastyGroup);
	}
}
