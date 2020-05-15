package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

import com.tdn.timeline.util.TimelineInstant;

public class Dynasty extends TimelineEvent {

	private List<Person> people	= null;
	private TimelineInstant headerStart = null;
	
	public Dynasty() {
		this.people = new ArrayList<Person>();
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

	public TimelineInstant getHeaderStart() {
		return headerStart;
	}
	public void setHeaderStart(TimelineInstant headerStart) {
		this.headerStart = headerStart;
	}

	public Person getFirstPerson() {
		Person result = null;
		if (people != null && people.size() > 0) {
			result = people.get(0);
		}
		return result;
	}

	public Person getLastPerson() {
		Person result = null;
		if (people != null && people.size() > 0) {
			result = people.get(people.size() - 1);
		}
		return result;
	}
}
