package com.tdn.timeline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdn.timeline.svg.TimelineSvgBuilder;
import com.tdn.util.FileUtilities;

public class TimelineBuilder {

	private String inputFile = null;
	private String id = null;

	public TimelineBuilder(String inputFile) {
		this.inputFile = inputFile;
		this.id = inputFile.split("\\.")[0];
		if (id.contains(File.separator)) {
			id = id.substring(id.lastIndexOf(File.separator) + 1);
		}
	}

	public Timeline generateTimeline() {
		System.out.println("Generating timeline " + id + " from " + inputFile);
		Timeline timeline = null;
		try {
			String json = FileUtilities.getFileContents(inputFile);
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(Timespan.class, new TimespanDeserializer())
					.create();
			timeline = gson.fromJson(json, Timeline.class);
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
		return timeline;
	}

	public static void main(String[] args) {

		if (args.length < 1) {
			System.err.println("Usgae: java -jar ./target/timeline-0.0.1-SNAPSHOT.jar /path/to/inputfile.json");
		}
		else {
			String inputFile = args[0];
			TimelineBuilder timelineBuilder = new TimelineBuilder(inputFile);
			Timeline timeline = timelineBuilder.generateTimeline();
			System.out.println(timeline);
		}
	}

}
