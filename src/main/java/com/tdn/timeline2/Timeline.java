package com.tdn.timeline2;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdn.util.FileUtilities;

public class Timeline extends TimelineEvent {
	private boolean addCenturyTickLines = false;
	private boolean addDecadeTickLines = false;
	private List<TimelineEvent> backgroundEvents;
	private List<DynastyGroup> politicalDynastyGroups;
	
	public Timeline() {
		backgroundEvents = new ArrayList<TimelineEvent>();
		politicalDynastyGroups = new ArrayList<DynastyGroup>();
	}

	public boolean isAddCenturyTickLines() {
		return addCenturyTickLines;
	}
	public void setAddCenturyTickLines(boolean addCenturyTickLines) {
		this.addCenturyTickLines = addCenturyTickLines;
	}

	public boolean isAddDecadeTickLines() {
		return addDecadeTickLines;
	}
	public void setAddDecadeTickLines(boolean addDecadeTickLines) {
		this.addDecadeTickLines = addDecadeTickLines;
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

	public static void main(String[] args) {
		String json = FileUtilities.getFileContents("src/main/resources/RomanHistory.json");
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Timespan.class, new TimespanDeserializer())
				.create();
		TimelineEvent timeline = gson.fromJson(json, Timeline.class);
		System.out.println(timeline);

	}
}
