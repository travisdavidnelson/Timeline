package com.tdn.timeline.model;

import java.util.ArrayList;
import java.util.List;

import com.tdn.timeline.Timeline;

public class Dynasty {
	
	int yStart = Timeline.dynastyStart;

	String name;
	List<Person> people = new ArrayList<Person>();
	
	public Dynasty() {
		
	}
	public Dynasty(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getPeople() {
		return people;
	}
	public void setPeople(List<Person> people) {
		this.people = people;
	}
	public void addPerson(Person lifetime) {
		this.people.add(lifetime);
	}
	
	public int getyStart() {
		return yStart;
	}
	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(name);
		result.append(": ");
		boolean isFirst = true;
		for(Person lifetime : people) {
			if (isFirst) {
				isFirst = false;
			}
			else {
				result.append(", ");
			}
			result.append(lifetime);
		}
		return result.toString();
	}
}
