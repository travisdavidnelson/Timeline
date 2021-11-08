package com.tdn.timeline.svg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.tdn.timeline.*;
import com.tdn.timeline.util.TimeUtilities;
import com.tdn.timeline.util.TimelineInstant;
import com.tdn.util.FileUtilities;
import com.tdn.util.ModelBuilder;

public class TimelineSvgBuilder {

	public static final String FADE_IN_MASK = "fadeInMask";
	public static final String FADE_OUT_MASK = "fadeOutMask";
	public static final String FADE_IN_OUT_MASK = "fadeInOutMask";

	private Timeline timeline;
	private String svg;

	private double slope = 0.0;
	private int width;
	private int yMax;
	private int xStart;
	private int yTimelineStart;
	private int majorTickHalfLength;
	private int minorTickHalfLength;
	private int tickHalfLength;
	private int dynastyStart;
	private int dynastyDiff;
	private int lifetimeYDiff;
	private int fateWidth;
	private int approximateYearBackgroundAdjustment;
	private int approximateYearPersonAdjustment;
	private int approximateYearTitleAdjustment;
	private int channelXOffset;
	private int channelYOffset;

	private TimelineInstant minDisplayInstant;
	private TimelineInstant maxDisplayInstant;

	private int nextPersonYStart = 0;
	private int maxLifetimeYEnd = 0;
	private Person lastPersonInGroup = null;
	private int lastPersonX;

	private List<Integer> timelineYPositions = new ArrayList<Integer>();

	private int channelCount = 0;
	private int backgroundEventCount = 0;
	private int dynastyCount = 0;
	private int peopleCount = 0;

	private String cssContents = null;

	public TimelineSvgBuilder(Timeline timeline, String cssContents) {
		this.timeline = timeline;
		this.minDisplayInstant = timeline.getTimespan().getStart();
		this.maxDisplayInstant = timeline.getTimespan().getEnd();

		this.cssContents = cssContents;

		xStart = timeline.getConfig().getxStart();
		this.width = 2 * xStart + getWidth( (minDisplayInstant.getDifferenceInDays(maxDisplayInstant)));
		System.out.println("Width is " + width + " pixels");
		this.yMax = timeline.getConfig().getHeightInPixels();
		System.out.println("Timeline height is " + yMax + " pixels");
		yTimelineStart = timeline.getConfig().getyTimelineStart();
		majorTickHalfLength = timeline.getConfig().getMajorTickHalfLength();
		minorTickHalfLength = timeline.getConfig().getMinorTickHalfLength();
		tickHalfLength = timeline.getConfig().getTickHalfLength();
		dynastyStart = yTimelineStart + timeline.getConfig().getDynastyStart();
		dynastyDiff = timeline.getConfig().getDynastyDiff();
		lifetimeYDiff = timeline.getConfig().getLifetimeYDiff();
		fateWidth = timeline.getConfig().getFateWidth();
		channelXOffset = timeline.getConfig().getChannelXOffset();
		channelYOffset = timeline.getConfig().getChannelYOffset();
		approximateYearBackgroundAdjustment = timeline.getConfig().getApproximateYearBackgroundAdjustment();
		approximateYearPersonAdjustment = timeline.getConfig().getApproximateYearPersonAdjustment();
		approximateYearTitleAdjustment = timeline.getConfig().getApproximateYearTitleAdjustment();
	}

	public String toSvgObject() {
		StringBuilder result = new StringBuilder();
		result.append("<object type=\"image/svg+xml\" data=\"./");
		result.append(timeline.getId());
		result.append(".svg\" width=\"");
		result.append(width);
		result.append("px\" height=\"");
		result.append(yMax);
		result.append("px\">");
		return result.toString();
	}

	public String toSVG() {
		if (svg == null) {
			StringBuilder foregroundStringBuilder = new StringBuilder();
			StringBuilder timelineStringBuilder = new StringBuilder();
			StringBuilder backgroundStringBuilderMin = new StringBuilder();
			StringBuilder backgroundStringBuilderMid = new StringBuilder();
			StringBuilder backgroundStringBuilderMax = new StringBuilder();
			int nextTimelineY = yTimelineStart;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, instantToX(minDisplayInstant), instantToX(maxDisplayInstant), "timeline"));
			timelineYPositions.add(nextTimelineY);
			dynastyStart = yTimelineStart + timeline.getConfig().getDynastyStart();
			int channelStart = yTimelineStart + timeline.getConfig().getDynastyStart();
			for (Channel channel : timeline.getChannels()) {
				try {
					System.out.println("  adding channel " + channel);
					String resources = channel.getResources();
					List<TimelineEvent> fullChannelBackgroundEvents = new ArrayList<>();
					for (String resource : resources.split(",")) {
						System.out.println("  processing resource " + resource);
						String filename = "history/" + resource + ".json";
						ModelBuilder<History> modelBuilder = new ModelBuilder<>();
						History history = modelBuilder.deserialize(History.class, filename);
						System.out.println("  adding history " + history);

						timelineStringBuilder.append(horizontalLine(channelStart, instantToX(minDisplayInstant), instantToX(maxDisplayInstant), "timeline"));

						for (DynastyGroup series : history.getDynastyGroups()) {
							if (!history.isStackable()) {
								if (!series.isPageBreak()) {
									dynastyStart = nextPersonYStart;
								} else {
									dynastyStart = channelStart;
								}
							}
							getDynastyGroupSVG(series, foregroundStringBuilder, backgroundStringBuilderMin);
						}
						fullChannelBackgroundEvents.addAll(history.getBackgroundEvents());
						for (DynastyGroup dynastyGroup : history.getDynastyGroups()) {
							fullChannelBackgroundEvents.addAll(dynastyGroup.getBackgroundEvents());
							for (Dynasty dynasty : dynastyGroup.getDynasties()) {
								Person firstPerson = dynasty.getFirstPerson();
								Person lastPerson = dynasty.getPeople().stream()
										.max(Comparator.comparing(Person::getyStart))
										.get();
								int yStart = dynasty.getyStart() - timeline.getConfig().getDynastyBackgroundTitleOffset();
								int yEnd = maxLifetimeYEnd + dynastyDiff;
								if (lastPerson != null) {
									yEnd = lastPerson.getyStart() + lastPerson.getHeight() + lifetimeYDiff;
								}

								int backgroundYStart = yStart - lifetimeYDiff;
								int backgroundYEnd = yEnd + lifetimeYDiff*2;
								for (TimelineEvent backgroundEvent : dynasty.getBackgroundEvents()) {
									getBackgroundSVG(backgroundEvent, backgroundYStart, backgroundYEnd, true, backgroundStringBuilderMin);
								}
							}
						}
					}

					for (TimelineEvent backgroundEvent : fullChannelBackgroundEvents) {
						System.out.println("  adding backgroundEvent " + backgroundEvent);
						getBackgroundSVG(backgroundEvent, channelStart, maxLifetimeYEnd + dynastyDiff, true, backgroundStringBuilderMid);
					}

					int textXStart = channelXOffset;
					int textYStart = maxLifetimeYEnd - channelYOffset;
					addTextSVG(channel.getName().toUpperCase(), textXStart, textYStart, "channel", foregroundStringBuilder);
					channelStart = maxLifetimeYEnd + dynastyDiff;
					timelineYPositions.add(channelStart);
					channelCount++;
				} catch (Exception e) {
					System.err.println("Caught exception processing channel " + channel);
					e.printStackTrace();
				}
			}
			dynastyStart = maxLifetimeYEnd + dynastyDiff;
			nextTimelineY = maxLifetimeYEnd + dynastyDiff;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, instantToX(minDisplayInstant), instantToX(maxDisplayInstant), "timeline"));
//			timelineYPositions.add(nextTimelineY);
//			firstNationYStart = maxLifetimeYEnd + 2*dynastyDiff;
//			nextNationYStart = firstNationYStart;
//			for (Nation nation : foreignNations) {
//				getNationSVG(nation, foregroundStringBuilder);
//			}
			nextTimelineY = maxLifetimeYEnd + dynastyDiff;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, instantToX(minDisplayInstant), instantToX(maxDisplayInstant), "timeline"));
			addGridlinesAndYearTextSVG(timelineStringBuilder);
			
			for (TimelineEvent backgroundEvent : timeline.getBackgroundEvents()) {
				getBackgroundSVG(backgroundEvent, yTimelineStart, nextTimelineY, true, backgroundStringBuilderMax);
			}

			StringBuilder result = new StringBuilder();
			addNameplate(result);
			result.append("<svg ");
			result.append("xmlns=\"http://www.w3.org/2000/svg\"\n");
			result.append("xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"");
			result.append(width);
			result.append("px\" height=\""+yMax+"px\" viewBox=\"0 0 ");
			result.append(width);
			result.append(" "+yMax+"\" enable-background=\"new 0 0 ");
			result.append(width);
			result.append(" "+yMax+"\" xml:space=\"preserve\">\n");
			result.append("<style>\n");
			result.append(cssContents);
			result.append("</style>\n");
			result.append(" " + getGradientDefs() + "\n");
			result.append(backgroundStringBuilderMax);
			result.append("\n");
			result.append(backgroundStringBuilderMid);
			result.append("\n");
			result.append(backgroundStringBuilderMin);
			result.append("\n");
			result.append(timelineStringBuilder);
			result.append("\n");
			result.append(foregroundStringBuilder);
			result.append("	</svg>\n");
			svg = result.toString();

			System.out.println();
			System.out.println("Channels: " + channelCount);
			System.out.println("Background events: " + backgroundEventCount);
			System.out.println("Dynasties: " + dynastyCount);
			System.out.println("People: " + peopleCount);
			System.out.println();
		}
		return svg;
	}
	public void getDynastyGroupSVG(DynastyGroup dynastyGroup, StringBuilder foregroundStringBuilder,
								   StringBuilder backgroundStringBuilder) {
		lastPersonInGroup = null;
		nextPersonYStart = dynastyStart;
		int markYStart = nextPersonYStart + dynastyDiff;
		for (Dynasty dynasty : dynastyGroup.getDynasties()) {
			if (dynasty.isMark()) {
				markYStart = nextPersonYStart + dynastyDiff;
			}
			getDynastySVG(dynasty, foregroundStringBuilder, backgroundStringBuilder, markYStart);
			Person lastPerson = dynasty.getPeople().stream()
					.max(Comparator.comparing(Person::getyStart))
					.get();
			nextPersonYStart = lastPerson.getyStart() + lastPerson.getHeight() + lifetimeYDiff;
		}
	}
	public void getDynastySVG(Dynasty dynasty, StringBuilder foregroundStringBuilder,
							  StringBuilder backgroundStringBuilder, int markYStart) {
		System.out.println("  adding dynasty "+dynasty);
		if (dynasty.isPageBreak()) {
			dynastyStart = markYStart;
		} else {
			dynastyStart = nextPersonYStart + dynastyDiff;
		}
		dynasty.setyStart(dynastyStart);
		nextPersonYStart = dynastyStart;
		TimelineInstant headerInstant = dynasty.getHeaderStart();
		int textYStart = nextPersonYStart;
		nextPersonYStart += lifetimeYDiff;
		for (Person lifetime : dynasty.getPeople()) {
			if (lifetime.isPageBreak()) {
				nextPersonYStart = textYStart + lifetimeYDiff;
			}
			lifetime.setyStart(nextPersonYStart);
			if (headerInstant == null) {
				TimelineInstant firstTitleInstant = lifetime.getFirstTitleStart();
				if (firstTitleInstant != null) {
					headerInstant = firstTitleInstant;
				}
				else {
					headerInstant = lifetime.getTimespan().getStart();
				}
			}
			String backgroundStyle = timeline.getConfig().getDefaultPersonStyle();
			if (lifetime.getStyle() != null) {
				backgroundStyle = lifetime.getStyle();
			}
			getPersonSVG(lifetime, backgroundStyle, null, foregroundStringBuilder);
		}
		int textXStart = instantToX(headerInstant);
		addTextSVG(dynasty.getName().toUpperCase(), textXStart, textYStart, "dynasty", foregroundStringBuilder);

		dynastyCount++;
	}
	public void getPersonSVG(Person person, String styleClass, String styleOverride, StringBuilder stringBuilder) {
		System.out.println("  adding lifetime "+person);
		if (lastPersonInGroup != null) {
//			if(!lastPersonInGroup.overlaps(person)) {
//				nextPersonYStart = dynastyStart;
//			}
		}
		String id = cleanForId(person.getName());
		String referencePage = "http://en.wikipedia.org/wiki/"+id;
		int x = lastPersonX;
		TimelineInstant start = person.getTimespan().getStart();
		if (start != null) {
			x = instantToX(max(start, timeline.getTimespan().getStart()));
		}
		if (person.getTimespan().getStartApproximate()) {
			x -= getWidth(approximateYearPersonAdjustment * 365);
		}
		long duration = person.getTimespan().getDuration(timeline.getTimespan());
		if (person.getTimespan().getStartApproximate()) {
			duration += approximateYearPersonAdjustment * 365;
		}
		if (person.getTimespan().getEndApproximate()) {
			duration += approximateYearPersonAdjustment * 365;
		}
		int width = getWidth(Long.valueOf(duration).intValue());
		stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
		int height = timeline.getConfig().getHeight(person.getImportance());
		stringBuilder.append(rectangle(id, x, nextPersonYStart, width, height, styleClass, styleOverride, getMaskName(person.getTimespan())));

		person.setyStart(nextPersonYStart);
		person.setxStart(x);
		person.setWidth(width);
		person.setHeight(height);

		int xOffset = timeline.getConfig().getXOffset(person.getImportance());
		int textX = x + xOffset;
		if (person.getTimespan().getStartApproximate()) {
			textX += xOffset;
		}
		int yOffset = timeline.getConfig().getYOffset(person.getImportance());
		stringBuilder.append("<title>");
		stringBuilder.append(person.getAnnotation());
		stringBuilder.append("</title>");
		for (TimelineEvent title : person.getTitles()) {
			getTitleSVG(person, title, title.getName(), stringBuilder);
		}
		for (TimelineEvent event : person.getForegroundEvents()) {
			getTitleSVG(person, event, event.getStyle(), stringBuilder);
		}
		addTextSVG(person.getName().toUpperCase(), textX, nextPersonYStart+yOffset, person.getImportance(), stringBuilder);
		stringBuilder.append("</a></g>\n");
		if (person.getFate() != null) {
			stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
			stringBuilder.append(rectangle(id, (x + width - fateWidth), nextPersonYStart, fateWidth, height, "fate", null, null));
			stringBuilder.append("<title>");
			stringBuilder.append(person.getFate());
			stringBuilder.append("</title>");
			stringBuilder.append("</a></g>\n");
		}
		nextPersonYStart += height + lifetimeYDiff;
		if (maxLifetimeYEnd < nextPersonYStart) {
			maxLifetimeYEnd = nextPersonYStart;
		}
		lastPersonInGroup = person;
		lastPersonX = x + width;

		peopleCount++;
	}
	private String cleanForId(String input) {
		return input
				.replaceAll(" ", "_")
				.replaceAll("á", "a")
				.replaceAll("é", "e")
				.replaceAll("è", "e")
				.replaceAll("É", "E")
				.replaceAll("ñ", "n")
				.replaceAll("ú", "u")
				.replaceAll("&#9792;", "");
	}
	private void getTitleSVG(Person person, TimelineEvent title, String style, StringBuilder stringBuilder) {
		TimelineInstant startInstant = title.getTimespan().getStart();
		int x = lastPersonX;
		if (startInstant != null) {
			x = instantToX(max(startInstant, timeline.getTimespan().getStart()));
		}
		if (title.getTimespan().getStartApproximate()) {
			x -= getWidth(approximateYearTitleAdjustment * 365);
		}
		int y = nextPersonYStart;
		long duration = title.getTimespan().getDuration(timeline.getTimespan());
		if (title.getTimespan().getStartApproximate()) {
			duration += approximateYearTitleAdjustment * 365;
		}
		if (title.getTimespan().getEndApproximate()) {
			duration += approximateYearTitleAdjustment * 365;
		}
		int width = getWidth(Long.valueOf(duration).intValue());
		int height = timeline.getConfig().getHeight(person.getImportance());
		stringBuilder.append("<g>");
		String rectString = rectangle(null, x, y, width, height, style, null, getMaskName(title.getTimespan()));
		stringBuilder.append(rectString);
		stringBuilder.append("<title>");
		stringBuilder.append(title.getAnnotation());
		stringBuilder.append("</title>");
		stringBuilder.append("</g>\n");
	}
	private void getBackgroundSVG(TimelineEvent backgroundEvent, int yStart, int yEnd, StringBuilder stringBuilder) {
		getBackgroundSVG(backgroundEvent, yStart, yEnd, false, stringBuilder);
	}
	private void getBackgroundSVG(TimelineEvent backgroundEvent, int yStart, int yEnd, boolean addFootprint, StringBuilder stringBuilder) {
		String id = backgroundEvent.getName().replaceAll(" ", "_");
		String referencePage = "http://en.wikipedia.org/wiki/"+id;
		int x = instantToX(max(backgroundEvent.getTimespan().getStart(), timeline.getTimespan().getStart()));
		long duration = backgroundEvent.getTimespan().getDuration(timeline.getTimespan());
		if (duration > approximateYearBackgroundAdjustment * 365 * 10) {
			if (backgroundEvent.getTimespan().getStartApproximate()) {
				x -= getWidth(approximateYearBackgroundAdjustment * 365);
			}
			if (backgroundEvent.getTimespan().getStartApproximate()) {
				duration += approximateYearBackgroundAdjustment * 365;
			}
			if (backgroundEvent.getTimespan().getEndApproximate()) {
				duration += approximateYearBackgroundAdjustment * 365;
			}
		}
		int width = getWidth(Long.valueOf(duration).intValue());
		int height = yEnd - yStart;
		stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
		if (addFootprint) {
			stringBuilder.append(rectangle(id, x, yStart, width, height, "footprint", null, getMaskName(backgroundEvent.getTimespan())));
		}
		stringBuilder.append(rectangle(id, x, yStart, width, height, backgroundEvent.getStyle(), null, getMaskName(backgroundEvent.getTimespan())));
		stringBuilder.append("<title>");
		stringBuilder.append(backgroundEvent.getAnnotation());
		stringBuilder.append("</title>");
		stringBuilder.append("</a></g>\n");

		backgroundEventCount++;
	}
	
	public void addGridlinesAndYearTextSVG(StringBuilder stringBuilder) {
		int majorTickineYears = timeline.getConfig().getMajorTickYears();
		int minorTickineYears = timeline.getConfig().getMinorTickYears();
		int halfMajorTicklineYears = majorTickineYears / 2;
		
		int startYear = minDisplayInstant.getYear();
		int endYear = maxDisplayInstant.getYear();
		for (int year = startYear; year <= endYear; year++) {
			if (year != 0) {
				if (year % majorTickineYears == 0 || year == 1) {
					TimelineInstant yearInstant = TimeUtilities.getInstant(year);
					int minY = 1000;
					int maxY = 0;
					for (int i = 0; i < timelineYPositions.size(); i++) {
						Integer yPosition = timelineYPositions.get(i);
						boolean isFirst = i == 0;
						boolean isLast = i == timelineYPositions.size() - 1;
						if (yPosition < minY) {
							minY = yPosition;
						}
						if (yPosition > maxY) {
							maxY = yPosition;
						}
						addMajorTickLineForYear(yearInstant, yPosition.intValue(), isFirst, isLast, stringBuilder);
					}
					addYearLine(yearInstant, minY, maxY, stringBuilder);
					String yearString = getYearString(year);
					int textXDiff = 20 * yearString.length();
					int topYearOffset = timeline.getConfig().getTopYearOffset();
					int bottomYearOffset = timeline.getConfig().getBottomYearOffset();
					String yearClass = "year";
					if (year % 1000 == 0 || year == 1) {
						yearClass = "yearMajor";
					}
					addTextSVG(yearString, instantToX(yearInstant)-textXDiff, minY - topYearOffset, yearClass, stringBuilder);
					addTextSVG(yearString, instantToX(yearInstant)-textXDiff, maxY + bottomYearOffset, yearClass, stringBuilder);
				}

				if (year % halfMajorTicklineYears == 0 && year % majorTickineYears != 0) {
					TimelineInstant yearInstant = TimeUtilities.getInstant(year);
					String yearString = getYearString(year);
					int textXDiff = 15 * yearString.length();
					int topYearOffset = 2 * timeline.getConfig().getTopYearOffset();
					String yearClass = "yearMinor";
					for (int i = 0; i < timelineYPositions.size(); i++) {
						Integer yPosition = timelineYPositions.get(i);
						boolean isFirst = i == 0;
						boolean isLast = i == timelineYPositions.size() - 1;
						addTickLineForYear(yearInstant, yPosition.intValue(), isFirst, isLast, stringBuilder);
						if (!isFirst && !isLast) {
							addTextSVG(yearString, instantToX(yearInstant) - textXDiff, yPosition - topYearOffset, yearClass, stringBuilder);
						}
					}
				}

				if (year % minorTickineYears == 0) {
					TimelineInstant yearInstant = TimeUtilities.getInstant(year);
					for (int i = 0; i < timelineYPositions.size(); i++) {
						Integer yPosition = timelineYPositions.get(i);
						boolean isFirst = i == 0;
						boolean isLast = i == timelineYPositions.size() - 1;
						addMinorTickLineForYear(yearInstant, yPosition.intValue(), isFirst, isLast, stringBuilder);
					}
				}
			}
		}
	}

	private String getYearString(int year) {
		String yearString = ""+Math.abs(year);
		if (year < 0) {
			yearString += " BC";
		}
		else if (year == 1) {
			yearString = "AD " + yearString;
		}
		return yearString;
	}

	public void addYearLine(TimelineInstant year, int yStart, int yEnd, StringBuilder stringBuilder) {
		int x = instantToX(year);
//		System.out.println("Adding year line for year " + year + " at x = " + x);
		stringBuilder.append(yearLine(x, yStart, yEnd));
	}
	public void addMajorTickLineForYear(TimelineInstant year, int yStart, boolean isFirst, boolean isLast, StringBuilder stringBuilder) {
		int x = instantToX(year);
//		System.out.println("Adding major tick line for year " + year + " at x = " + x);
		stringBuilder.append(majorTickLine(x, yStart, isFirst, isLast));
	}
	public void addMinorTickLineForYear(TimelineInstant year, int yStart, boolean isFirst, boolean isLast, StringBuilder stringBuilder) {
		int x = instantToX(year);
//		System.out.println("Adding minor tick line for year " + year + " at x = " + x);
		stringBuilder.append(minorTickLine(x, yStart, isFirst, isLast));
	}
	public void addTickLineForYear(TimelineInstant year, int yStart, boolean isFirst, boolean isLast, StringBuilder stringBuilder) {
		int x = instantToX(year);
//		System.out.println("Adding minor tick line for year " + year + " at x = " + x);
		stringBuilder.append(tickLine(x, yStart, isFirst, isLast));
	}

	public int getWidth(int duration) {
		return  Math.max(1, (int) Math.round(slope() * duration));
	}
	
	public void addTextSVG(String text, int x, int y, String className, StringBuilder stringBuilder) {
		stringBuilder.append("\n<text x=\"");
		stringBuilder.append(x);
		stringBuilder.append("\" y=\"");
		stringBuilder.append(y);
		stringBuilder.append("\" class=\"");
		stringBuilder.append(className);
		stringBuilder.append("\">");
		stringBuilder.append(text);
		stringBuilder.append("</text>");

	}

	public String toHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		addNameplate(result);
		result.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n");
		result.append("<head>\n");
		result.append("  <title>");
		result.append(timeline.getName());
		result.append("</title>\n");
		result.append("  <link type=\"text/css\" rel=\"stylesheet\" href=\"./");
		result.append(timeline.getId());
		result.append(".css\"/>");
		result.append("</head>\n");
		result.append("<body>\n");
		result.append(toSvgObject());
		result.append("</body>\n");
		result.append("</html>\n ");
		return result.toString();
	}

	private void addNameplate(StringBuilder result) {
		String author = timeline.getAuthor();
		if (author == null) {
			author = "Travis David Nelson";
		}
		result.append("<!--\n     Generated by ");
		result.append(author);
		result.append(" on ");
		Calendar c = Calendar.getInstance();
		result.append(String.format("%1tc\n-->\n", c));
	}

	protected TimelineInstant max(TimelineInstant i1, TimelineInstant i2) {
		TimelineInstant result = i1;
		if (result == null || (i2 != null && i2.isAfter(i1))) {
			result = i2;
		}
		return result;
	}

	protected int instantToX(TimelineInstant instant) {
		return (int) (Math.round(slope() * minDisplayInstant.getDifferenceInDays(instant)) + xStart);
	}
	
	protected double slope() {
		if (slope == 0.0) {
			slope = timeline.getConfig().getPixelsPerYear() / 365.25;
			System.out.println("Timeline has " + slope + " pixels/day");
		}
		return slope;
	}	

	
	
	public int majorTickY1(int yStart) {
		return yStart - majorTickHalfLength;
	}
	public int majorTickY2(int yStart) {
		return yStart + majorTickHalfLength;
	}
	public int minorTickY1(int yStart) {
		return yStart - minorTickHalfLength;
	}
	public int minorTickY2(int yStart) {
		return yStart + minorTickHalfLength;
	}
	public int tickY1(int yStart) {
		return yStart - tickHalfLength;
	}
	public int tickY2(int yStart) {
		return yStart + tickHalfLength;
	}

	public String yearLine(int x, int yStart, int yEnd) {
		return verticalLine(x, yStart, yEnd, "timeline");
	}
	public String majorTickLine(int x, int yStart, boolean isFirst, boolean isLast) {
		int y1 = isFirst ? yStart : majorTickY1(yStart);
		int y2 = isLast ? yStart : majorTickY2(yStart);
		return verticalLine(x, y1, y2, "timeline");
	}
	public String minorTickLine(int x, int yStart, boolean isFirst, boolean isLast) {
		int y1 = isFirst ? yStart : minorTickY1(yStart);
		int y2 = isLast ? yStart : minorTickY2(yStart);
		return verticalLine(x, y1, y2, "timeline");
	}
	public String tickLine(int x, int yStart, boolean isFirst, boolean isLast) {
		int y1 = isFirst ? yStart : tickY1(yStart);
		int y2 = isLast ? yStart : tickY2(yStart);
		return verticalLine(x, y1, y2, "timeline");
	}

	public String horizontalLine(int y, int x1, int x2, String styleClass) {
		return line(x1, y, x2, y, styleClass);
	}
	public String verticalLine(int x, int y1, int y2, String styleClass) {
		return line(x, y1, x, y2, styleClass);
	}
	public String line(int x1, int y1, int x2, int y2, String styleClass) {
		StringBuilder result = new StringBuilder();
		result.append("		<line x1=\"");
		result.append(x1);
		result.append("\" ");
		result.append("y1=\"");
		result.append(y1);
		result.append("\" ");
		result.append("x2=\"");
		result.append(x2);
		result.append("\" ");
		result.append("y2=\"");
		result.append(y2);
		result.append("\" class=\""+styleClass+"\"/>\n");
		return result.toString();
	}
	public String rectangle(String id, int x, int y, int width, int height, String styleClass, String styleOverride, String maskName) {
		StringBuilder result = new StringBuilder();
		result.append("<rect ");
		if (id != null) {
			result.append("id=\""+id+"\" ");
		}
		result.append("x=\"");
		result.append(x);
		result.append("\" y=\"");
		result.append(y);
		result.append("\" width=\"");
		result.append(width);
		result.append("\" height=\"");
		result.append(height);
		result.append("\" class=\""+styleClass+"\"");
		if (styleOverride != null) {
			result.append(" style=\""+styleOverride+"\"");
		}
		if (maskName != null) {
			result.append("  mask=\"url(#"+maskName+")\"");
		}
		result.append("/>");
		return result.toString();
	}
	
	private String getGradientDefs() {
		String result = "gradientDefs.txt not found";
		try {
			result = FileUtilities.getFileContents("src/main/resources/gradientDefs.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	private String getMaskName(Timespan timespan) {
		String result = null;
		if (timespan.getStartApproximate() && !timespan.getEndApproximate()) {
			result = FADE_IN_MASK;
		}
		else if (timespan.getEndApproximate() && !timespan.getStartApproximate()) {
			result = FADE_OUT_MASK;
		}
		else if (timespan.getStartApproximate() && timespan.getEndApproximate()) {
			result = FADE_IN_OUT_MASK;
		}
		return result;
	}

}
