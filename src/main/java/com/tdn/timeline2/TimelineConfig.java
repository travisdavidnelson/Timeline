package com.tdn.timeline2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TimelineConfig extends TimelineEvent {
	private boolean addCenturyTickLines = false;
	private boolean addDecadeTickLines = false;
	private String defaultPersonStyle = null;
	private int pixelsPerYear = 0;
	private int heightInPixels = 0;
	private Map<String, Integer> importanceHeights = null;
	private Map<String, Integer> importanceOffsets = null;
	
	public TimelineConfig() {
		importanceHeights = new TreeMap<String, Integer>();
		importanceOffsets = new TreeMap<String, Integer>();
	}

	public boolean getAddCenturyTickLines() {
		return addCenturyTickLines;
	}
	public void setAddCenturyTickLines(boolean addCenturyTickLines) {
		this.addCenturyTickLines = addCenturyTickLines;
	}

	public boolean getAddDecadeTickLines() {
		return addDecadeTickLines;
	}
	public void setAddDecadeTickLines(boolean addDecadeTickLines) {
		this.addDecadeTickLines = addDecadeTickLines;
	}

	public String getDefaultPersonStyle() {
		return defaultPersonStyle;
	}
	public void setDefaultPersonStyle(String defaultPersonStyle) {
		this.defaultPersonStyle = defaultPersonStyle;
	}

	public int getPixelsPerYear() {
		return pixelsPerYear;
	}
	public void setPixelsPerYear(int pixelsPerYear) {
		this.pixelsPerYear = pixelsPerYear;
	}

	public int getHeightInPixels() {
		return heightInPixels;
	}
	public void setHeightInPixels(int heightInPixels) {
		this.heightInPixels = heightInPixels;
	}

	public Map<String, Integer> getImportanceHeights() {
		return importanceHeights;
	}
	public void setImportanceHeights(Map<String, Integer> importanceHeights) {
		this.importanceHeights = importanceHeights;
	}
	public void addImportanceHeights(String key, Integer height) {
		this.importanceHeights.put(key, height);
	}
	public Integer getHeight(String importance) {
		Integer result = null;
		if (importanceHeights.containsKey(importance)) {
			result = importanceHeights.get(importance);
		}
		else {
			System.err.println("No height for importance " + importance);
		}
		return result;
	}

	public Map<String, Integer> getImportanceOffsets() {
		return importanceOffsets;
	}
	public void setImportanceOffsets(Map<String, Integer> importanceOffsets) {
		this.importanceOffsets = importanceOffsets;
	}
	public Integer getOffset(String importance) {
		Integer result = null;
		if (importanceOffsets.containsKey(importance)) {
			result = importanceOffsets.get(importance);
		}
		else {
			System.err.println("No offset for importance " + importance);
		}
		return result;
	}
}
