package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

public class DynastyGroup extends TimelineEvent {
	private int yStart;
	private List<Dynasty> dynasties = null;;
	
	public DynastyGroup() {
		dynasties = new ArrayList<Dynasty>();
	}

	public int getyStart() {
		return yStart;
	}
	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public List<Dynasty> getDynasties() {
		return dynasties;
	}
	public void setDynasties(List<Dynasty> dynasties) {
		this.dynasties = dynasties;
	}
	public void addDynasty(Dynasty dynasty) {
		this.dynasties.add(dynasty);
	}
}
