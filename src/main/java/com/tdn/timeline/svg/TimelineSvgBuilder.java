package com.tdn.timeline.svg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.tdn.timeline.*;
import com.tdn.timeline.util.TimeUtilities;
import com.tdn.timeline.util.TimelineInstant;
import com.tdn.util.FileUtilities;

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

	public TimelineSvgBuilder(Timeline timeline) {
		this.timeline = timeline;
		this.minDisplayInstant = timeline.getTimespan().getStart();
		this.maxDisplayInstant = timeline.getTimespan().getEnd();

		xStart = timeline.getConfig().getxStart();
		this.width = 2 * xStart + getWidth( (minDisplayInstant.getDifferenceInDays(maxDisplayInstant)));
		System.out.println("Width is " + width + " pixels");
		this.yMax = timeline.getConfig().getHeightInPixels();
		System.out.println("Timeline height is " + yMax + " pixels");
		yTimelineStart = timeline.getConfig().getyTimelineStart();
		majorTickHalfLength = timeline.getConfig().getMajorTickHalfLength();
		minorTickHalfLength = timeline.getConfig().getMinorTickHalfLength();
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
	
	public String toSVG() {
		if (svg == null) {
			StringBuilder foregroundStringBuilder = new StringBuilder();
			StringBuilder timelineStringBuilder = new StringBuilder();
			StringBuilder backgroundStringBuilder = new StringBuilder();
			int nextTimelineY = yTimelineStart;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, instantToX(minDisplayInstant), instantToX(maxDisplayInstant), "timeline"));
			timelineYPositions.add(nextTimelineY);
			dynastyStart = yTimelineStart + timeline.getConfig().getDynastyStart();
			int channelStart = yTimelineStart + timeline.getConfig().getDynastyStart();
			for (TimelineChannel channel : timeline.getChannels()) {
				System.out.println("  adding channel "+channel);
				timelineStringBuilder.append(horizontalLine(channelStart, instantToX(minDisplayInstant), instantToX(maxDisplayInstant), "timeline"));
				int textXStart = channelXOffset;
				int textYStart = channelStart + dynastyDiff + channelYOffset;
				addTextSVG(channel.getName().toUpperCase(), textXStart, textYStart, "channel", foregroundStringBuilder);
				for (DynastyGroup series : channel.getDynastyGroups()) {
					dynastyStart = channelStart;
					getDynastyGroupSVG(series, foregroundStringBuilder, backgroundStringBuilder);
				}
				for (TimelineEvent backgroundEvent : channel.getBackgroundEvents()) {
					getBackgroundSVG(backgroundEvent, channelStart, maxLifetimeYEnd + dynastyDiff, backgroundStringBuilder);
				}
				channelStart = maxLifetimeYEnd + dynastyDiff;
				channelCount++;
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
			timelineYPositions.add(nextTimelineY);
			addGridlinesSVG(timelineStringBuilder);
			
			for (TimelineEvent backgroundEvent : timeline.getBackgroundEvents()) {
				getBackgroundSVG(backgroundEvent, yTimelineStart, nextTimelineY, backgroundStringBuilder);
			}

			StringBuilder result = new StringBuilder();
			result.append("	<?xml-stylesheet type=\"text/css\" href=\"./");
			result.append(timeline.getId());
			result.append(".css\" ?>");
			result.append("	<svg x=\"0px\" y=\"0px\" width=\"");
			result.append(width);
			result.append("px\" height=\""+yMax+"px\" viewBox=\"0 0 ");
			result.append(width);
			result.append(" "+yMax+"\" enable-background=\"new 0 0 ");
			result.append(width);
			result.append(" "+yMax+"\" xml:space=\"preserve\">\n");
			result.append(" " + getGradientDefs() + "\n");
			result.append(backgroundStringBuilder);
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
		for (Dynasty dynasty : dynastyGroup.getDynasties()) {
			getDynastySVG(dynasty, foregroundStringBuilder, backgroundStringBuilder);
		}
	}
	public void getDynastySVG(Dynasty dynasty, StringBuilder foregroundStringBuilder,
							  StringBuilder backgroundStringBuilder) {
		System.out.println("  adding dynasty "+dynasty);
		dynastyStart = nextPersonYStart + dynastyDiff;
		nextPersonYStart = dynastyStart;
		TimelineInstant headerInstant = dynasty.getHeaderStart();
		int textYStart = nextPersonYStart;
		nextPersonYStart += lifetimeYDiff;
		for (Person lifetime : dynasty.getPeople()) {
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
		String id = person.getName().replaceAll(" ", "_");
		String referencePage = "http://en.wikipedia.org/wiki/"+id;
		int x = lastPersonX;
		TimelineInstant start = person.getTimespan().getStart();
		if (start != null) {
			x = instantToX(start);
		}
		if (person.getTimespan().getStartApproximate()) {
			x -= getWidth(approximateYearPersonAdjustment * 365);
		}
		long duration = person.getTimespan().getDuration();
		if (person.getTimespan().getStartApproximate()) {
			duration += approximateYearPersonAdjustment * 365;
		}
		if (person.getTimespan().getEndApproximate()) {
			duration += approximateYearPersonAdjustment * 365;
		}
		int width = getWidth(Long.valueOf(duration).intValue());
		stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
		int height = timeline.getConfig().getHeight(person.getImportance());
		stringBuilder.append(rectangle(id, x, nextPersonYStart, width, height, "footprint", null, getMaskName(person.getTimespan())));
		stringBuilder.append(rectangle(id, x, nextPersonYStart, width, height, styleClass, styleOverride, getMaskName(person.getTimespan())));
		for (TimelineEvent title : person.getTitles()) {
			getTitleSVG(person, title, title.getName(), stringBuilder);
		}
		for (TimelineEvent event : person.getForegroundEvents()) {
			getTitleSVG(person, event, event.getStyle(), stringBuilder);
		}
		int xOffset = timeline.getConfig().getXOffset(person.getImportance());
		int textX = x + xOffset;
		if (person.getTimespan().getStartApproximate()) {
			textX += xOffset;
		}
		int yOffset = timeline.getConfig().getYOffset(person.getImportance());
		addTextSVG(person.getName().toUpperCase(), textX, nextPersonYStart+yOffset, person.getImportance(), stringBuilder);
		stringBuilder.append("<title>");
		stringBuilder.append(person.getAnnotation());
		stringBuilder.append("</title>");
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
	private void getTitleSVG(Person person, TimelineEvent title, String style, StringBuilder stringBuilder) {
		TimelineInstant startInstant = title.getTimespan().getStart();
		int x = lastPersonX;
		if (startInstant != null) {
			x = instantToX(startInstant);
		}
		if (title.getTimespan().getStartApproximate()) {
			x -= getWidth(approximateYearTitleAdjustment * 365);
		}
		int y = nextPersonYStart;
		long duration = title.getTimespan().getDuration();
		if (title.getTimespan().getStartApproximate()) {
			duration += approximateYearTitleAdjustment * 365;
		}
		if (title.getTimespan().getEndApproximate()) {
			duration += approximateYearTitleAdjustment * 365;
		}
		int width = getWidth(Long.valueOf(duration).intValue());
		int height = timeline.getConfig().getHeight(person.getImportance());
		String rectString = rectangle(null, x, y, width, height, style, null, getMaskName(title.getTimespan()));
		stringBuilder.append(rectString);
	}
	private void getBackgroundSVG(TimelineEvent backgroundEvent, int yStart, int nextTimelineY, StringBuilder stringBuilder) {
		String id = backgroundEvent.getName().replaceAll(" ", "_");
		String referencePage = "http://en.wikipedia.org/wiki/"+id;
		int x = instantToX(backgroundEvent.getTimespan().getStart());
		if (backgroundEvent.getTimespan().getStartApproximate()) {
			x -= getWidth(approximateYearBackgroundAdjustment * 365);
		}
		long duration = backgroundEvent.getTimespan().getDuration(timeline.getTimespan().getEnd());
		if (backgroundEvent.getTimespan().getStartApproximate()) {
			duration += approximateYearBackgroundAdjustment * 365;
		}
		if (backgroundEvent.getTimespan().getEndApproximate()) {
			duration += approximateYearBackgroundAdjustment * 365;
		}
		int width = getWidth(Long.valueOf(duration).intValue());
		stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
		stringBuilder.append(rectangle(id, x, yStart, width, (nextTimelineY - yStart), backgroundEvent.getStyle(), null, getMaskName(backgroundEvent.getTimespan())));
		stringBuilder.append("<title>");
		stringBuilder.append(backgroundEvent.getAnnotation());
		stringBuilder.append("</title>");
		stringBuilder.append("</a></g>\n");

		backgroundEventCount++;
	}
	
	public void addGridlinesSVG(StringBuilder stringBuilder) {
		int majorTickineYears = timeline.getConfig().getMajorTickYears();
		int minorTickineYears = timeline.getConfig().getMinorTickYears();
		
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
					String yearString = ""+Math.abs(year);
					if (year < 0) {
						yearString += " BC";
					}
					else if (year == 1) {
						yearString = "AD " + yearString;
					}
					int textXDiff = 10 * yearString.length();
					int topYearOffset = timeline.getConfig().getTopYearOffset();
					int bottomYearOffset = timeline.getConfig().getBottomYearOffset();
					addTextSVG(yearString, instantToX(yearInstant)-textXDiff, minY - topYearOffset, "year", stringBuilder);
					addTextSVG(yearString, instantToX(yearInstant)-textXDiff, maxY + bottomYearOffset, "year", stringBuilder);
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
		String author = timeline.getAuthor();
		if (author == null) {
			author = "Travis David Nelson";
		}
		result.append("<!--\n     Generated by ");
		result.append(author);
		result.append(" on ");
		Calendar c = Calendar.getInstance();
		result.append(String.format("%1tc\n-->\n", c));
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
		result.append(toSVG());
		result.append("</body>\n");
		result.append("</html>\n ");
		return result.toString();
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
			result.append("\" style=\""+styleOverride+"\"");
		}
		if (maskName != null) {
			result.append("\"  mask=\"url(#"+maskName+")\"");
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
