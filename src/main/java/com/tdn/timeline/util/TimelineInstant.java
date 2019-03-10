package com.tdn.timeline.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class TimelineInstant {

	public static TimelineInstant NOW = null;
	static {
		LocalDateTime now = LocalDateTime.now();
		int year = now.get(ChronoField.YEAR);
		int month = now.get(ChronoField.MONTH_OF_YEAR);
		int day = now.get(ChronoField.DAY_OF_MONTH);
		NOW = TimeUtilities.getInstant(year, month, day);
	}

	private String originalString;
	private Instant instant;
	private int year;
	
	public TimelineInstant(String originalString, Instant instant, int year) {
		this.originalString = originalString;
		this.instant = instant;
		this.year = year;
	}
	
	public String getOriginalString() {
		return originalString;
	}
	public Instant getInstant() {
		return instant;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getDifferenceInDays(TimelineInstant other) {
		return getDifferenceInDays(other.getInstant());
	}

	public int getDifferenceInDays(Instant other) {
		return (int) this.instant.until(other, ChronoUnit.DAYS);
	}
	
	public boolean isAfter(TimelineInstant other) {
		return this.getInstant().isAfter(other.getInstant());
	}
	
	public boolean isBefore(TimelineInstant other) {
		return this.getInstant().isBefore(other.getInstant());
	}

	@Override
	public String toString() {
		return originalString + " <" + instant.toString() + ">";
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (TimelineInstant.class.isInstance(otherObj)) {
			TimelineInstant other = (TimelineInstant) otherObj;
			return this.getInstant().equals(other.getInstant());
		} else {
			return false;
		}
	}
}
