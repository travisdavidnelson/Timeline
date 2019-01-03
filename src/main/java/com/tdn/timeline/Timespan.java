package com.tdn.timeline;

public class Timespan {
	public static final String FADE_IN_MASK = "fadeInMask";
	public static final String FADE_OUT_MASK = "fadeOutMask";
	public static final String FADE_IN_OUT_MASK = "fadeInOutMask";

	private int startYear;
	private int endYear;
	private boolean startYearApproximate = false;;
	private boolean endYearApproximate = false;

	public Timespan() {
		
	}
	
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
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
	
	public int getDuration() {
		int result = endYear - startYear;
		// Year Zero doesn't exist
		if (startYear < 0 && endYear > 0)
			result = result - 1;
		// Duration at least a year
		if (result == 0) {
			result = 1;
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getYearString(startYear, startYearApproximate));
		result.append(" - ");
		result.append(getYearString(endYear, endYearApproximate));
		return result.toString();
	}

	private String getYearString(int year, boolean approximate) {
		StringBuilder result = new StringBuilder();
		if (approximate) {
			result.append("c. ");
		}
		result.append(Math.abs(year));
		if (year < 0) {
			result.append(" BC");
		}
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
