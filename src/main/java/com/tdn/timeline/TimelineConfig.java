package com.tdn.timeline;

import java.util.Map;
import java.util.TreeMap;

public class TimelineConfig extends TimelineEvent {
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
	private int approximateYearBackgroundAdjustment = 0;
	private int approximateYearPersonAdjustment = 0;
	private int approximateYearTitleAdjustment = 0;
	private Map<String, Integer> importanceHeights = null;
	private Map<String, Integer> importanceYOffsets = null;
	private Map<String, Integer> importanceXOffsets = null;
	private int majorTickYears = 100;
	private int minorTickYears = 10;
	private int topYearOffset = 10;
	private int bottomYearOffset = 40;
	
	public TimelineConfig() {
		importanceHeights = new TreeMap<String, Integer>();
		importanceYOffsets = new TreeMap<String, Integer>();
		importanceXOffsets = new TreeMap<String, Integer>();
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

	public int getApproximateYearBackgroundAdjustment() {
		return approximateYearBackgroundAdjustment;
	}

	public void setApproximateYearBackgroundAdjustment(int approximateYearBackgroundAdjustment) {
		this.approximateYearBackgroundAdjustment = approximateYearBackgroundAdjustment;
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

	public Map<String, Integer> getImportanceYOffsets() {
		return importanceYOffsets;
	}
	public void setImportanceYOffsets(Map<String, Integer> importanceOffsets) {
		this.importanceYOffsets = importanceOffsets;
	}
	public Integer getYOffset(String importance) {
		Integer result = null;
		if (importanceYOffsets.containsKey(importance)) {
			result = importanceYOffsets.get(importance);
		}
		else {
			System.err.println("No y offset for importance " + importance);
		}
		return result;
	}

	public Map<String, Integer> getImportanceXOffsets() {
		return importanceXOffsets;
	}
	public void setImportanceXOffsets(Map<String, Integer> importanceOffsets) {
		this.importanceXOffsets = importanceOffsets;
	}
	public Integer getXOffset(String importance) {
		Integer result = null;
		if (importanceXOffsets.containsKey(importance)) {
			result = importanceXOffsets.get(importance);
		}
		else {
			System.err.println("No x offset for importance " + importance);
		}
		return result;
	}

	public int getMajorTickYears() {
		return majorTickYears;
	}
	public void setMajorTickYears(int majorTickYears) {
		this.majorTickYears = majorTickYears;
	}

	public int getMinorTickYears() {
		return minorTickYears;
	}
	public void setMinorTickYears(int minorTickYears) {
		this.minorTickYears = minorTickYears;
	}

	public int getTopYearOffset() {
		return topYearOffset;
	}

	public void setTopYearOffset(int topYearOffset) {
		this.topYearOffset = topYearOffset;
	}

	public int getBottomYearOffset() {
		return bottomYearOffset;
	}

	public void setBottomYearOffset(int bottomYearOffset) {
		this.bottomYearOffset = bottomYearOffset;
	}
}
