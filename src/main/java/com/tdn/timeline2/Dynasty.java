package com.tdn.timeline2;

import java.util.ArrayList;
import java.util.List;

public class Dynasty extends TimelineEvent {

	List<Person> people	= null;
	
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
}
