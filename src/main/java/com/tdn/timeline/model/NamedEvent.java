package com.tdn.timeline.model;

public class NamedEvent {

	protected String name;
	protected DateRange dateRange;
	protected String annotation;
	protected String style;

	public NamedEvent() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	public String getAnnotation() {
		return annotation != null ? annotation : toString();
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return name + " (" + dateRange + ")";
	}
	
	public String getWikipediaId() {
		return getName().replaceAll(" ", "_");
	}
	public String getWikipediaLink() {
		String result = "http://en.wikipedia.org/wiki/"+getWikipediaId();
		return result;
	}
	
	public String getNameCamelCase() {
		StringBuilder result = new StringBuilder();
		boolean toUpper = false;
		boolean add = true;
		for (char c : name.toCharArray()) {
			if (c == ' ') {
				toUpper = true;
				add = false;
			}
			else {
				add = true;
			}
			if (add) {
				if (toUpper) {
					result.append(Character.toUpperCase(c));
				}
				else {
					result.append(Character.toLowerCase(c));
				}
				toUpper = false;
			}
		}
		return result.toString();
	}
	
	public boolean overlaps(NamedEvent other) {
		return (this.getDateRange().getStartYear() >= other.getDateRange().getStartYear() && this.getDateRange().getStartYear() <= other.getDateRange().getEndYear()) ||
        (this.getDateRange().getEndYear() >= other.getDateRange().getStartYear() && this.getDateRange().getEndYear() <= other.getDateRange().getEndYear()) ||
        (other.getDateRange().getStartYear() >= this.getDateRange().getStartYear() && other.getDateRange().getStartYear() <= this.getDateRange().getEndYear()) ||
        (other.getDateRange().getEndYear() >= this.getDateRange().getStartYear() && other.getDateRange().getEndYear() <= this.getDateRange().getEndYear());		
	}

}