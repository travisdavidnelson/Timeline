package com.tdn.timeline;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tdn.timeline.svg.TimelineSvgBuilder;
import com.tdn.util.FileUtilities;
import com.tdn.util.ModelBuilder;

public class TimelineBuilder {

	private File projectRoot = null;
	private File inputFile = null;
	private String id = null;

	public TimelineBuilder(File projectRoot, File inputFile) {
		this.projectRoot = projectRoot;
		this.inputFile = inputFile;
		String absolutePath = inputFile.getAbsolutePath();
		this.id = absolutePath.split("\\.")[0];
		if (id.contains(File.separator)) {
			id = id.substring(id.lastIndexOf(File.separator) + 1);
		}
	}

	public Timeline generateTimeline() {
		System.out.println("Generating timeline " + id + " from " + inputFile);
		Timeline timeline = null;
		try {
			ModelBuilder<Timeline> modelBuilder = new ModelBuilder<>();
			timeline = modelBuilder.deserialize(Timeline.class, inputFile);
			if (timeline != null) {
				timeline.setId(id);
				System.out.println(timeline);
				TimelineSvgBuilder svgBuilder = new TimelineSvgBuilder(timeline);
	
				File outputFolder = new File(projectRoot, "output");
				if (!outputFolder.exists()) {
					outputFolder.mkdirs();
				}
	
				File originalCssFile = new File(projectRoot, id + ".css");
				String cssContents = FileUtilities.getFileContents(originalCssFile);
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
			else {
				System.err.println("Parsed Timeline for input file " + inputFile.getAbsolutePath() + " is null.");
			}
			System.out.println("Done generating timeline " + id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return timeline;
	}

	public static void main(String[] args) {

		if (args.length < 1) {
			System.err.println("Usage: java -jar ./target/timeline-0.0.1-SNAPSHOT.jar /path/to/root/ TimelineFolder*");
		}
		else {
			String rootPath = args[0];
			File root = new File(rootPath);
			if (root.exists()) {
				if (root.isDirectory()) {
					String[] projects = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null;
					if (projects == null) {
						projects = root.list(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								return new File (dir, name).isDirectory();
							}
						});
					}
					List<String> projectNames = Arrays.asList(projects);
					System.out.println("Found projects " + projectNames);
					for (String project : projectNames) {
						System.out.println("Found project folder " + project);
						File projectFile = new File (root, project);
						File[] dataFiles = projectFile.listFiles(new FilenameFilter() {
								@Override
								public boolean accept(File dir, String name) {
									return name.endsWith(".json");
								}
							});
						List<String> dataFileNames = new ArrayList<>();
						for (File dataFile : dataFiles) {
							dataFileNames.add(dataFile.getAbsolutePath());
						}
						System.out.println("Found dataFiles " + dataFileNames);
						for (File dataFile : dataFiles) {
							TimelineBuilder timelineBuilder = new TimelineBuilder(projectFile, dataFile);
							Timeline timeline = timelineBuilder.generateTimeline();
							System.out.println(timeline);
						}
					}
				}
				else {
					System.err.println("Root path is not a directory " + rootPath);
				}
			}
			else {
				System.err.println("Root path does not exist " + rootPath);
			}
		}
	}

}
