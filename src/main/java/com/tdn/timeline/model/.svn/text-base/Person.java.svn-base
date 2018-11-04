package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Person")
@XmlType(propOrder = { "lifespan", "importance", "fate", "backgroundStyle", "titles" })
public class Person extends NamedEvent {
	
	public static String TRANSFORMATIONAL = "transformational";
	public static String MAJOR = "major";
	public static String SEMI_MAJOR = "semimajor";
	public static String MINOR = "minor";
	
	String importance = MINOR;
	String fate = null;
	String backgroundStyle = null;
    List<Title> titles = new ArrayList<Title>();
	
	public Person() {
		
	}
	public Person(String name, DateRange lifespan) {
		setName(name);
		setLifespan(lifespan);
	}
	
	public DateRange getLifespan() {
		return getDateRange();
	}
	public void setLifespan(DateRange lifespan) {
		this.setDateRange(lifespan);
	}
	
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	
	public String getFate() {
		return fate;
	}
	public void setFate(String fate) {
		this.fate = fate;
	}
	
	public int getHeight() {
		if (MINOR.equals(importance)) {
			return 6;
		}
		else if (SEMI_MAJOR.equals(importance)) {
			return 10;
		}
		else if (MAJOR.equals(importance)) {
			return 15;
		}
		else {
			return 20;
		}
	}
	
	public String getBackgroundStyle() {
		return backgroundStyle;
	}
	public void setBackgroundStyle(String backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}
	public List<Title> getTitles() {
		return titles;
	}
	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}
	public void addTitles(Title title) {
		this.titles.add(title);
	}
	
}
