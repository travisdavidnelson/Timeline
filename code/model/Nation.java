package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Nation")
@XmlType(propOrder = { "timespan", "people", "conflicts" })
public class Nation extends NamedEvent {
	
	List<Person> people = new ArrayList<Person>();
	List<NamedEvent> conflicts = new ArrayList<NamedEvent>();
	
	public Nation() {
		
	}
	
	public DateRange getTimespan() {
		return getDateRange();
	}
	public void setTimespan(DateRange timespan) {
		this.setDateRange(timespan);
	}
	
	public List<Person> getPeople() {
		return people;
	}
	public void setPeople(List<Person> people) {
		this.people = people;
	}
	public void addPerson(Person person) {
		this.people.add(person);
	}
	
	public List<NamedEvent> getConflicts() {
		return conflicts;
	}
	public void setConflicts(List<NamedEvent> conflicts) {
		this.conflicts = conflicts;
	}

	public int getHeight() {
		int maxSeries = 0;
		int result = Timeline.lifetimeYDiff;
		Person lastPerson = null;
		for (Person person : people) {
			if (lastPerson != null) {
				if (!lastPerson.overlaps(person)) {
					if (maxSeries < result) {
						maxSeries = result;
					}
					result = Timeline.lifetimeYDiff;
				}
			}
			result += person.getHeight();
			result += Timeline.lifetimeYDiff;
			
			lastPerson = person;
		}
		if (result < maxSeries) {
			result = maxSeries;
		}
		return result;
	}

}
