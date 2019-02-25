package com.tdn.timeline;

import com.tdn.timeline.util.TimelineInstant;

public class Timespan {
	public static final String FADE_IN_MASK = "fadeInMask";
	public static final String FADE_OUT_MASK = "fadeOutMask";
	public static final String FADE_IN_OUT_MASK = "fadeInOutMask";

	private TimelineInstant start;
	private TimelineInstant end;
	private boolean startYearApproximate = false;
	private boolean endYearApproximate = false;

	public Timespan() {
		
	}
	
	public boolean getStartYearApproximate() {
		return startYearApproximate;
	}
	public void setStartYearApproximate(boolean startYearApproximate) {
		this.startYearApproximate = startYearApproximate;
	}

	public boolean getEndYearApproximate() {
		return endYearApproximate;
	}
	public void setEndYearApproximate(boolean endYearApproximate) {
		this.endYearApproximate = endYearApproximate;
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
		long result = 0;
		if (end != null) {
			result = start.getDifferenceInDays(end);
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getYearString(start, startYearApproximate));
		if (end != null) {
			result.append(" - ");
			result.append(getYearString(end, endYearApproximate));
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

	public String getMaskName() {
		String result = null;
		if (getStartYearApproximate() && !getEndYearApproximate()) {
			result = FADE_IN_MASK;
		}
		else if (getEndYearApproximate() && !getStartYearApproximate()) {
			result = FADE_OUT_MASK;
		}
		else if (getStartYearApproximate() && getEndYearApproximate()) {
			result = FADE_IN_OUT_MASK;
		}
		return result;
	}
}
