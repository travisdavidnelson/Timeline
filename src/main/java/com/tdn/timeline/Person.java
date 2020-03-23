package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

import com.tdn.timeline.util.TimelineInstant;

public class Person extends TimelineEvent {
	public static String TRANSFORMATIONAL = "transformational";
	public static String MAJOR = "major";
	public static String SEMI_MAJOR = "semimajor";
	public static String MINOR = "minor";
	public static String PETTY = "petty";

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

	public TimelineInstant getFirstTitleStart() {
		TimelineInstant result = null;
		if (this.titles != null && this.titles.size() > 0) {
			result = this.titles.get(0).getTimespan().getStart();
		}
		return result;
	}

	@Override
	public Timespan getTimespan() {
		Timespan result = super.getTimespan();
		if (result == null) {
			result = inferTimespan();
			setTimespan(result);
		}
		return result;
	}

	private Timespan inferTimespan() {
		Timespan result;
		TimelineInstant earliest = null;
		TimelineInstant latest = null;
		long longestDuration = 0L;
		if (titles != null) {
			for (TimelineEvent event : titles) {
				if (earliest == null || earliest.isAfter(event.getTimespan().getStart())) {
					earliest = event.getTimespan().getStart();
				}
				if (latest == null || latest.isBefore(event.getTimespan().getEnd())) {
					latest = event.getTimespan().getEnd();
				}
				if (longestDuration == 0L || event.getTimespan().getDuration() > longestDuration) {
					longestDuration = event.getTimespan().getDuration();
				}
			}
		}
		if (foregroundEvents != null) {
			for (TimelineEvent event : foregroundEvents) {
				if (earliest == null || earliest.isAfter(event.getTimespan().getStart())) {
					earliest = event.getTimespan().getStart();
				}
				if (latest == null || latest.isBefore(event.getTimespan().getEnd())) {
					latest = event.getTimespan().getEnd();
				}
				if (longestDuration == 0L || event.getTimespan().getDuration() > longestDuration) {
					longestDuration = event.getTimespan().getDuration();
				}
			}
		}
		result = new Timespan();
		result.setStart(earliest);
		result.setEnd(latest);
		result.setDuration(longestDuration);
		return result;
	}
}
