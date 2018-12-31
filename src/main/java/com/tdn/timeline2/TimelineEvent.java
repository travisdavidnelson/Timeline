package com.tdn.timeline2;

public class TimelineEvent {

	private String name;
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
}
