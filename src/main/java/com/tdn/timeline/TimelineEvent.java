package com.tdn.timeline;


public class TimelineEvent {

	private String name;
	private String annotation;
	private String style;
	private Timespan timespan = null;

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
}
