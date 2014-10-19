package model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Title")
@XmlType(propOrder = { "reign" })
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
