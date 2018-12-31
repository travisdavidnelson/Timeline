package com.tdn.timeline2;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends TimelineEvent {
	private String id = null;
	private TimelineConfig config = null;
	
	private List<TimelineEvent> backgroundEvents;
	private List<DynastyGroup> politicalDynastyGroups;
	
	public Timeline() {
		config = new TimelineConfig();
		
		backgroundEvents = new ArrayList<TimelineEvent>();
		politicalDynastyGroups = new ArrayList<DynastyGroup>();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public void addPoliticalDynastyGroups(DynastyGroup dynastyGroup) {
		this.politicalDynastyGroups.add(dynastyGroup);
	}
}
