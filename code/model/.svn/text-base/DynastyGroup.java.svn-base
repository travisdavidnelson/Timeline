package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DynastyGroup")
@XmlType(propOrder = { "name", "yStart", "dynasties" })
public class DynastyGroup {
	
	int yStart = Timeline.dynastyStart;

	String name;
	List<Dynasty> dynasties = new ArrayList<Dynasty>();

	public DynastyGroup() {
		
	}
	
	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dynasty> getDynasties() {
		return dynasties;
	}

	public void setDynasties(List<Dynasty> dynasties) {
		this.dynasties = dynasties;
	}

	public void addDynasty(Dynasty dynasty) {
		this.dynasties.add(dynasty);;
	}

	@Override
	public String toString() {
		return name + " " + dynasties;
	}
}
