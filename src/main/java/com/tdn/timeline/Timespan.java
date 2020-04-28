package com.tdn.timeline;

import com.tdn.timeline.util.TimelineInstant;

public class Timespan {
	private TimelineInstant start;
	private TimelineInstant end;
	private long duration;
	private boolean startApproximate = false;
	private boolean endApproximate = false;

	public Timespan() {
		
	}
	
	public boolean getStartApproximate() {
		return startApproximate;
	}
	public void setStartApproximate(boolean startApproximate) {
		this.startApproximate = startApproximate;
	}

	public boolean getEndApproximate() {
		return endApproximate;
	}
	public void setEndApproximate(boolean endApproximate) {
		this.endApproximate = endApproximate;
	}
	
	public TimelineInstant getStart() {
		return start;
	}
	public void setStart(TimelineInstant start) {
		this.start = start;
	}

	public TimelineInstant getEnd() {
		return end;
	}
	public void setEnd(TimelineInstant end) {
		this.end = end;
	}

	public long getDuration() {
		return getDuration(null);
	}
	public long getDuration(TimelineInstant hardStop) {
		if (duration == 0) {
			TimelineInstant durationEnd = TimelineInstant.NOW;
			if (end != null) {
				durationEnd = end;
			}
			if (hardStop != null && hardStop.isBefore(durationEnd)) {
				durationEnd = hardStop;
			}
			duration = start.getDifferenceInDays(durationEnd);
		}
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (start != null) {
			result.append(getYearString(start, startApproximate));
		}
		if (end != null) {
			result.append(" - ");
			result.append(getYearString(end, endApproximate));
		}
		return result.toString();
	}

	private String getYearString(TimelineInstant instant, boolean approximate) {
		StringBuilder result = new StringBuilder();
		if (approximate) {
			result.append("c. ");
		}
		result.append(instant.getOriginalString());
		return result.toString();
	}
}
