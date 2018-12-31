package com.tdn.timeline2;

public class Timespan {
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
}
