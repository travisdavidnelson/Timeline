package com.tdn.timeline;


import java.util.ArrayList;
import java.util.List;

public class TimelineEvent {

	protected List<TimelineEvent> backgroundEvents;
	private String name;
	private String annotation;
	private String style;
	private boolean pageBreak = false;
	private Timespan timespan = null;

	private transient int yStart;
	private transient int xStart;
	private transient int width;
	private transient int height;

	public TimelineEvent() {
		backgroundEvents = new ArrayList<TimelineEvent>();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public String getAnnotation() {
		return annotation != null ? annotation : toString();
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public Timespan getTimespan() {
		return timespan;
	}
	public void setTimespan(Timespan timespan) {
		this.timespan = timespan;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(name);
		if (timespan != null) {
			result.append(" (");
			result.append(timespan.toString());
			result.append(")");
		}
		return result.toString();
	}

	public boolean overlaps(TimelineEvent other) {
		return (this.getTimespan().getStart().isAfter(other.getTimespan().getStart()) && this.getTimespan().getStart().isBefore(other.getTimespan().getEnd())) ||
        (this.getTimespan().getEnd().isAfter(other.getTimespan().getStart()) && this.getTimespan().getEnd().isBefore(other.getTimespan().getEnd())) ||
        (other.getTimespan().getStart().isAfter(this.getTimespan().getStart()) && other.getTimespan().getStart().isBefore(this.getTimespan().getEnd())) ||
        (other.getTimespan().getEnd().isAfter(this.getTimespan().getStart()) && other.getTimespan().getEnd().isBefore(this.getTimespan().getEnd()));		
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

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isPageBreak() {
		return pageBreak;
	}

	public void setPageBreak(boolean pageBreak) {
		this.pageBreak = pageBreak;
	}
}
