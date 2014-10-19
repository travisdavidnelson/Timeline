package model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DateRange")
@XmlType(propOrder = { "startYearApproximate", "startYear", "endYearApproximate", "endYear" })
public class DateRange {

	boolean startYearApproximate = false;
	int startYear;
	boolean endYearApproximate = false;
	int endYear;
	
	public DateRange() {
		
	}
	public DateRange(int startYear, int endYear) {
		this.startYear = startYear;
		this.endYear = endYear;
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
	
	public boolean isStartYearApproximate() {
		return startYearApproximate;
	}
	public void setStartYearApproximate(boolean startYearApproximate) {
		this.startYearApproximate = startYearApproximate;
	}
	public boolean isEndYearApproximate() {
		return endYearApproximate;
	}
	public void setEndYearApproximate(boolean endYearApproximate) {
		this.endYearApproximate = endYearApproximate;
	}
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getYearString(startYear, startYearApproximate));
		if (endYear > startYear) {
			result.append(" - ");
			result.append(getYearString(endYear, endYearApproximate));
		}
		return result.toString();
	}
	
	public static String getYearString(int year, boolean approximate) {
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
