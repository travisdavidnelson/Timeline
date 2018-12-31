package com.tdn.timeline2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdn.timeline2.svg.TimelineSvgBuilder;
import com.tdn.util.FileUtilities;

public class Timeline extends TimelineEvent {
	private String id = null;
	private boolean addCenturyTickLines = false;
	private boolean addDecadeTickLines = false;
	private String defaultPersonStyle = null;
	private int pixelsPerYear = 0;
	private int heightInPixels = 0;
	private List<TimelineEvent> backgroundEvents;
	private List<DynastyGroup> politicalDynastyGroups;
	
	public Timeline() {
		backgroundEvents = new ArrayList<TimelineEvent>();
		politicalDynastyGroups = new ArrayList<DynastyGroup>();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public boolean isAddCenturyTickLines() {
		return addCenturyTickLines;
	}
	public void setAddCenturyTickLines(boolean addCenturyTickLines) {
		this.addCenturyTickLines = addCenturyTickLines;
	}

	public boolean isAddDecadeTickLines() {
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

	public List<TimelineEvent> getBackgroundEvents() {
		return backgroundEvents;
	}
	public void setBackgroundEvents(List<TimelineEvent> backgroundEvents) {
		this.backgroundEvents = backgroundEvents;
	}
	public void addBackgroundEvent(TimelineEvent backgroundEvent) {
		this.backgroundEvents.add(backgroundEvent);
	}

	public List<DynastyGroup> getPoliticalDynastyGroups() {
		return politicalDynastyGroups;
	}
	public void setPoliticalDynastyGroups(List<DynastyGroup> politicalDynastyGroups) {
		this.politicalDynastyGroups = politicalDynastyGroups;
	}
	public void addPoliticalDynastyGroups(DynastyGroup dynastyGroup) {
		this.politicalDynastyGroups.add(dynastyGroup);
	}

	private static final String HTML_TARGET = "./RomanHistoryTimeline.html";
	private static final String SVG_TARGET = "./RomanHistoryTimeline.svg";

	public static void main(String[] args) {
		
		String id = "RomanHistory";
		try {
			String json = FileUtilities.getFileContents("src/main/resources/" + id + ".json");
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(Timespan.class, new TimespanDeserializer())
					.create();
			Timeline timeline = gson.fromJson(json, Timeline.class);
			timeline.setId(id);
			System.out.println(timeline);
			TimelineSvgBuilder svgBuilder = new TimelineSvgBuilder(timeline);

			File outputFolder = new File("./output");
			if (!outputFolder.exists()) {
				outputFolder.mkdirs();
			}
		
			String cssContents = FileUtilities.getFileContents("src/main/resources/" + id + ".css");
			File cssFile = new File(outputFolder, "" + id + ".css");
			System.out.println("Writing CSS file " + cssFile);
			FileWriter fileWriter = new FileWriter(cssFile);
			fileWriter.append(cssContents);
			fileWriter.flush();
			fileWriter.close();

			File htmlFile = new File(outputFolder, "" + id + ".html");
			System.out.println("Writing HTML file " + htmlFile);
			fileWriter = new FileWriter(htmlFile);
			fileWriter.append(svgBuilder.toHTML());
			fileWriter.flush();
			fileWriter.close();
		
			File svgFile = new File(outputFolder, "" + id + ".svg");
			System.out.println("Writing SVG file " + svgFile);
			fileWriter = new FileWriter(svgFile);
			fileWriter.append(svgBuilder.toSVG());
			fileWriter.flush();
			fileWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("Done generating timeline " + id);
	}
}
