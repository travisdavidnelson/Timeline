package com.tdn.timeline2;

import java.util.Map;
import java.util.TreeMap;

public class TimelineConfig extends TimelineEvent {
	private boolean addCenturyTickLines = false;
	private boolean addDecadeTickLines = false;
	private String defaultPersonStyle = null;
	private int pixelsPerYear = 0;
	private int heightInPixels = 0;
	private int xStart = 0;
	private int yTimelineStart = 0;
	private int majorTickHalfLength = 0;
	private int minorTickHalfLength = 0;
	private int dynastyStart = 0;
	private int dynastyDiff = 0;
	private int lifetimeYDiff = 0;
	private int fontSize = 0;
	private int fateWidth = 0;
	private int approximateYearPersonAdjustment = 0;
	private int approximateYearTitleAdjustment = 0;
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

	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyTimelineStart() {
		return yTimelineStart;
	}

	public void setyTimelineStart(int yTimelineStart) {
		this.yTimelineStart = yTimelineStart;
	}

	public int getMajorTickHalfLength() {
		return majorTickHalfLength;
	}

	public void setMajorTickHalfLength(int majorTickHalfLength) {
		this.majorTickHalfLength = majorTickHalfLength;
	}

	public int getMinorTickHalfLength() {
		return minorTickHalfLength;
	}

	public void setMinorTickHalfLength(int minorTickHalfLength) {
		this.minorTickHalfLength = minorTickHalfLength;
	}

	public int getDynastyStart() {
		return dynastyStart;
	}

	public void setDynastyStart(int dynastyStart) {
		this.dynastyStart = dynastyStart;
	}

	public int getDynastyDiff() {
		return dynastyDiff;
	}

	public void setDynastyDiff(int dynastyDiff) {
		this.dynastyDiff = dynastyDiff;
	}

	public int getLifetimeYDiff() {
		return lifetimeYDiff;
	}

	public void setLifetimeYDiff(int lifetimeYDiff) {
		this.lifetimeYDiff = lifetimeYDiff;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getFateWidth() {
		return fateWidth;
	}

	public void setFateWidth(int fateWidth) {
		this.fateWidth = fateWidth;
	}

	public int getApproximateYearPersonAdjustment() {
		return approximateYearPersonAdjustment;
	}

	public void setApproximateYearPersonAdjustment(int approximateYearPersonAdjustment) {
		this.approximateYearPersonAdjustment = approximateYearPersonAdjustment;
	}

	public int getApproximateYearTitleAdjustment() {
		return approximateYearTitleAdjustment;
	}

	public void setApproximateYearTitleAdjustment(int approximateYearTitleAdjustment) {
		this.approximateYearTitleAdjustment = approximateYearTitleAdjustment;
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
