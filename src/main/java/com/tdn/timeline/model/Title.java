package com.tdn.timeline.model;

public class Title extends NamedEvent {
	
	@Override
	public String toString() {
		return name + " "+ dateRange;
	}
	
	public DateRange getReign() {
		return getDateRange();
	}
	public void setReign(DateRange reign) {
		setDateRange(reign);
	}
	
}
