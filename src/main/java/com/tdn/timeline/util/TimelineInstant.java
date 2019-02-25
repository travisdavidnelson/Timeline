package com.tdn.timeline.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TimelineInstant {
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
		return (int) this.instant.until(other.getInstant(), ChronoUnit.DAYS);
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
