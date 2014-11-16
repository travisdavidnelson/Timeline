package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.events.StartDocument;

import util.FileUtilities;

@XmlRootElement(namespace = "model", name = "Timeline")
@XmlType(propOrder = { "range", "addCenturyTickLines", "addDecadeTickLines", "backgroundEvents", "politicalDynastyGroups", "culturalDynastyGroups", "foreignNations" })
public class Timeline extends NamedEvent {
	
	public static int xStart = 100;
	public static int xEnd = 100 + (2 * 2300);
	public static int yTimelineStart = 20;
	public static int yMax = 1500;
	public static int majorTickHalfLength = 7;
	public static int minorTickHalfLength = 5;
	public static int dynastyStart = 25;
	public static int dynastyDiff = 25;
	public static int lifetimeYDiff = 5;
	public static int fontSize = 10;
	public static int minDisplayYear = -800;
	public static int maxDisplayYear = 1500;
	public static int fateWidth = 5;

	boolean addCenturyTickLines = true;
	boolean addDecadeTickLines = true;
	List<NamedEvent> backgroundEvents = new ArrayList<NamedEvent>();
	List<DynastyGroup> politicalDynastyGroups = new ArrayList<DynastyGroup>();
	List<DynastyGroup> culturalDynastyGroups = new ArrayList<DynastyGroup>();
	List <Nation> foreignNations = new ArrayList<Nation>();
	
	String svg = null;
	
	private double slope = 0.0;
	
	private int nextPersonYStart = 50;
	private int maxLifetimeYEnd = 0;
	private Person lastPersonInGroup = null;
	
	private int firstNationYStart = 0;
	private int lastNationYStart = 0;
	private int nextNationYStart = 0;
	private Nation lastNation = null;

	private List<Integer> timelineYPositions = new ArrayList<Integer>();
	
	public Timeline() {
		
	}
	public Timeline(String name, DateRange range) {
		this.name = name;
		setRange(range);
	}
	
	
	
	public DateRange getRange() {
		return getDateRange();
	}
	public void setRange(DateRange range) {
		setDateRange(range);
	}

	public List<NamedEvent> getBackgroundEvents() {
		return backgroundEvents;
	}
	public void setBackgroundEvents(List<NamedEvent> backgroundEvents) {
		this.backgroundEvents = backgroundEvents;
	}
	public void addBackgroundEvent(NamedEvent backgroundEvent) {
		backgroundEvents.add(backgroundEvent);
	}
	
	public List<DynastyGroup> getPoliticalDynastyGroups() {
		return politicalDynastyGroups;
	}
	public void setPoliticalDynastyGroups(List<DynastyGroup> dynastyGroups) {
		this.politicalDynastyGroups = dynastyGroups;
	}
	public void addPoliticalDynastyGroup(DynastyGroup series) {
		politicalDynastyGroups.add(series);
	}

	public List<DynastyGroup> getCulturalDynastyGroups() {
		return culturalDynastyGroups;
	}
	public void setCulturalDynastyGroups(List<DynastyGroup> dynastyGroups) {
		this.culturalDynastyGroups = dynastyGroups;
	}
	public void addCulturalDynastyGroup(DynastyGroup series) {
		culturalDynastyGroups.add(series);
	}

	public List<Nation> getForeignNations() {
		return foreignNations;
	}
	public void setForeignNations(List<Nation> foreignNations) {
		this.foreignNations = foreignNations;
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
	@Override
	public String toString() {
		return name + " ("+getRange()+")";
	}

	public static int majorTickY1(int yStart) {
		return yStart - majorTickHalfLength;
	}
	public static int majorTickY2(int yStart) {
		return yStart + majorTickHalfLength;
	}
	public static int minorTickY1(int yStart) {
		return yStart - minorTickHalfLength;
	}
	public static int minorTickY2(int yStart) {
		return yStart + minorTickHalfLength;
	}

	public static String yearLine(int x, int yStart, int yEnd) {
		return verticalLine(x, yStart, yEnd, "timeline");
	}
	public static String majorTickLine(int x, int yStart, boolean isFirst, boolean isLast) {
		int y1 = isFirst ? yStart : majorTickY1(yStart);
		int y2 = isLast ? yStart : majorTickY2(yStart);
		return verticalLine(x, y1, y2, "timeline");
	}
	public static String minorTickLine(int x, int yStart, boolean isFirst, boolean isLast) {
		int y1 = isFirst ? yStart : minorTickY1(yStart);
		int y2 = isLast ? yStart : minorTickY2(yStart);
		return verticalLine(x, y1, y2, "timeline");
	}
	
	public static String horizontalLine(int y, int x1, int x2, String styleClass) {
		return line(x1, y, x2, y, styleClass);
	}
	public static String verticalLine(int x, int y1, int y2, String styleClass) {
		return line(x, y1, x, y2, styleClass);
	}
	public static String line(int x1, int y1, int x2, int y2, String styleClass) {
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
	public static String rectangle(String id, int x, int y, int width, int height, String styleClass, String styleOverride, String maskName) {
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

	public String toSVG() {
		if (svg == null) {
			StringBuilder foregroundStringBuilder = new StringBuilder();
			StringBuilder timelineStringBuilder = new StringBuilder();
			int nextTimelineY = yTimelineStart;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, yearMapping(minDisplayYear), yearMapping(maxDisplayYear), "timeline"));
			timelineYPositions.add(nextTimelineY);
			for (DynastyGroup series : politicalDynastyGroups) {
				dynastyStart = 25;
				getDynastyGroupSVG(series, foregroundStringBuilder);
			}
			dynastyStart = maxLifetimeYEnd + dynastyDiff;
			for (DynastyGroup series : culturalDynastyGroups) {
				getDynastyGroupSVG(series, foregroundStringBuilder);
			}
//			dynastyStart = maxLifetimeYEnd + dynastyDiff;
			nextTimelineY = maxLifetimeYEnd + dynastyDiff;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, yearMapping(minDisplayYear), yearMapping(maxDisplayYear), "timeline"));
			timelineYPositions.add(nextTimelineY);
			firstNationYStart = maxLifetimeYEnd + 2*dynastyDiff;
			nextNationYStart = firstNationYStart;
			for (Nation nation : foreignNations) {
				getNationSVG(nation, foregroundStringBuilder);
			}
			nextTimelineY = maxLifetimeYEnd + dynastyDiff;
			timelineStringBuilder.append(horizontalLine(nextTimelineY, yearMapping(minDisplayYear), yearMapping(maxDisplayYear), "timeline"));
			timelineYPositions.add(nextTimelineY);
			addCenturyAndDecadeTickLinesSVG(timelineStringBuilder);
			
			StringBuilder backgroundStringBuilder = new StringBuilder();
			for (NamedEvent backgroundEvent : backgroundEvents) {
				getBackgroundSVG(backgroundEvent, nextTimelineY, backgroundStringBuilder);
			}

			StringBuilder result = new StringBuilder();
			result.append("	<?xml-stylesheet type=\"text/css\" href=\"./RomeStyles.css\" ?>");
			result.append("	<svg x=\"0px\" y=\"0px\" width=\"");
			result.append(xEnd + xStart);
			result.append("px\" height=\""+yMax+"px\" viewBox=\"0 0 ");
			result.append(xEnd + xStart);
			result.append(" "+yMax+"\" enable-background=\"new 0 0 ");
			result.append(xEnd + xStart);
			result.append(" "+yMax+"\" xml:space=\"preserve\">\n");
			result.append(" " + getGradientDefs() + "\n");
			result.append(backgroundStringBuilder);
			result.append("\n");
			result.append(timelineStringBuilder);
			result.append("\n");
			result.append(foregroundStringBuilder);
			result.append("	</svg>\n");
			svg = result.toString();
		}
		return svg;
	}
	public String toHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		result.append("<!--\n     Generated by Travis David Nelson on ");
		Calendar c = Calendar.getInstance();
		result.append(String.format("%1tc\n-->\n", c));
		result.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n");
		result.append("<head>\n");
		result.append("  <title>Timeline</title>\n");
		result.append("  <link type=\"text/css\" rel=\"stylesheet\" href=\"./RomeStyles.css\"/>");
		result.append("</head>\n");
		result.append("<body>\n");
		result.append(toSVG());
		result.append("</body>\n");
		result.append("</html>\n ");
		return result.toString();
	}
	
	public void addCenturyAndDecadeTickLinesSVG(StringBuilder stringBuilder) {
		for (int year = minDisplayYear; year <= maxDisplayYear; year++) {
			if (year != 0) {
				if (addCenturyTickLines && (year % 100 == 0 || year == 1)) {
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
						addMajorTickLineForYear(year, yPosition.intValue(), isFirst, isLast, stringBuilder);
					}
					addYearLine(year, minY, maxY, stringBuilder);
					String yearString = ""+Math.abs(year);
					if (year < 0) {
						yearString += " BC";
					}
					else if (year == 1) {
						yearString = "AD " + yearString;
					}
					int textXDiff = 4*yearString.length();
					addTextSVG(yearString, yearMapping(year)-textXDiff, minY - 7, "year", stringBuilder);
					addTextSVG(yearString, yearMapping(year)-textXDiff, maxY + 20, "year", stringBuilder);
				}
				if (addDecadeTickLines && year % 10 == 0) {
					for (int i = 0; i < timelineYPositions.size(); i++) {
						Integer yPosition = timelineYPositions.get(i);
						boolean isFirst = i == 0;
						boolean isLast = i == timelineYPositions.size() - 1;
						addMinorTickLineForYear(year, yPosition.intValue(), isFirst, isLast, stringBuilder);
					}
				}
			}
		}
	}

	public void addYearLine(int year, int yStart, int yEnd, StringBuilder stringBuilder) {
		stringBuilder.append(yearLine(yearMapping(year), yStart, yEnd));
	}
	public void addMajorTickLineForYear(int year, int yStart, boolean isFirst, boolean isLast, StringBuilder stringBuilder) {
		stringBuilder.append(majorTickLine(yearMapping(year), yStart, isFirst, isLast));
	}
	public void addMinorTickLineForYear(int year, int yStart, boolean isFirst, boolean isLast, StringBuilder stringBuilder) {
		stringBuilder.append(minorTickLine(yearMapping(year), yStart, isFirst, isLast));
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
	public void getDynastyGroupSVG(DynastyGroup dynastyGroup, StringBuilder stringBuilder) {
		lastPersonInGroup = null;
		nextPersonYStart = dynastyStart + dynastyGroup.getyStart();
		for (Dynasty dynasty : dynastyGroup.getDynasties()) {
			getDynastySVG(dynasty, stringBuilder);
		}
	}
	public void getDynastySVG(Dynasty dynasty, StringBuilder stringBuilder) {
		dynastyStart = nextPersonYStart + dynastyDiff;
		nextPersonYStart = dynastyStart;
		int firstYearOfFirstPerson = 0;
		int textYStart = nextPersonYStart-5;
		for (Person lifetime : dynasty.getPeople()) {
			if (firstYearOfFirstPerson == 0 || firstYearOfFirstPerson > lifetime.getLifespan().getStartYear()) {
				firstYearOfFirstPerson = lifetime.getLifespan().getStartYear();
			}
			String backgroundStyle = "roman";
			if (lifetime.getBackgroundStyle() != null) {
				backgroundStyle = lifetime.getBackgroundStyle();
			}
			getPersonSVG(lifetime, backgroundStyle, null, stringBuilder);
		}
		int textXStart = yearMapping(firstYearOfFirstPerson);
		addTextSVG(dynasty.getName().toUpperCase(), textXStart, textYStart, "dynasty", stringBuilder);
	}
	public void getPersonSVG(Person person, String styleClass, String styleOverride, StringBuilder stringBuilder) {
		if (lastPersonInGroup != null) {
			if(!lastPersonInGroup.overlaps(person)) {
				nextPersonYStart = dynastyStart;
			}
		}
		String id = person.getName().replaceAll(" ", "_");
		String referencePage = "http://en.wikipedia.org/wiki/"+id;
		int x = yearMapping(person.getLifespan().getStartYear());
		int width = (int) (slope() * person.getLifespan().getDuration());
		stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
		stringBuilder.append(rectangle(id, x, nextPersonYStart, width, person.getHeight(), "footprint", null, person.getLifespan().getMaskName()));
		stringBuilder.append(rectangle(id, x, nextPersonYStart, width, person.getHeight(), styleClass, styleOverride, person.getLifespan().getMaskName()));
		for (Title title : person.getTitles()) {
			getTitleSVG(person, title, stringBuilder);
		}
		int yOffset = 5;
		if (Person.TRANSFORMATIONAL.equals(person.getImportance())) {
			yOffset = 12;
		}
		else if (Person.MAJOR.equals(person.getImportance())) {
			yOffset = 10;
		}
		if (Person.SEMI_MAJOR.equals(person.getImportance())) {
			yOffset = 8;
		}
		addTextSVG(person.getName().toUpperCase(), x+2, nextPersonYStart+yOffset, person.getImportance(), stringBuilder);
		stringBuilder.append("<title>");
		stringBuilder.append(person.getAnnotation());
		stringBuilder.append("</title>");
		stringBuilder.append("</a></g>\n");
		if (person.getFate() != null) {
			stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
			stringBuilder.append(rectangle(id, (x + width - fateWidth), nextPersonYStart, fateWidth, person.getHeight(), "fate", null, null));
			stringBuilder.append("<title>");
			stringBuilder.append(person.getFate());
			stringBuilder.append("</title>");
			stringBuilder.append("</a></g>\n");
		}
		System.out.println("  adding lifetime "+person);
		nextPersonYStart += person.getHeight()+ lifetimeYDiff;
		if (maxLifetimeYEnd < nextPersonYStart) {
			maxLifetimeYEnd = nextPersonYStart;
		}
		lastPersonInGroup = person;
	}
	private void getTitleSVG(Person person, Title title, StringBuilder stringBuilder) {
		int x = yearMapping(title.getDateRange().getStartYear());
		int y = nextPersonYStart;
		int width = (int) (slope() * title.getDateRange().getDuration());
		int height = person.getHeight();
		stringBuilder.append(rectangle(null, x, y, width, height, title.getName(), null, title.getDateRange().getMaskName()));
	}
	private void getBackgroundSVG(NamedEvent backgroundEvent, int nextTimelineY, StringBuilder stringBuilder) {
		String id = backgroundEvent.getName().replaceAll(" ", "_");
		String referencePage = "http://en.wikipedia.org/wiki/"+id;
		int x = yearMapping(backgroundEvent.getDateRange().getStartYear());
		int width = (int) (slope() * backgroundEvent.getDateRange().getDuration());
		stringBuilder.append("<g><a xlink:href=\""+referencePage+"\" target=\"_blank\">");
		stringBuilder.append(rectangle(id, x, yTimelineStart, width, (nextTimelineY - yTimelineStart), backgroundEvent.getStyle(), null, backgroundEvent.getDateRange().getMaskName()));
		stringBuilder.append("<title>");
		stringBuilder.append(backgroundEvent.getAnnotation());
		stringBuilder.append("</title>");
		stringBuilder.append("</a></g>\n");
	}

	
	protected int yearMapping(int year) {
		if (year > 0) {
			year = year - 1;
		}
		return (int) slope() * (year - getRange().getStartYear()) + xStart;
	}
	
	protected double slope() {
		if (slope == 0.0) {
			slope = (double) (xEnd - xStart) / (getRange().getEndYear() - getRange().getStartYear());
			System.out.println("Calculated slope as "+slope+" pixels/year");
		}
		return slope;
	}	
	public void getNationSVG(Nation nation, StringBuilder stringBuilder) {
		
		if (lastNation != null) {
			if (lastNation.getDateRange().getStartYear() < nation.getDateRange().getStartYear() && !lastNation.overlaps(nation)) {
				nextNationYStart = firstNationYStart;
			}
			else if (lastNation.getTimespan().getEndYear() <= nation.getTimespan().getStartYear()) {
				nextNationYStart = lastNationYStart;
			}
		}
		int x = yearMapping(nation.getTimespan().getStartYear());
		int width = (int) (slope() * nation.getTimespan().getDuration());
		int height = nation.getHeight();
		stringBuilder.append("<g><a xlink:href=\""+nation.getWikipediaLink()+"\" target=\"_blank\">");
		stringBuilder.append(rectangle(nation.getWikipediaId(), x, nextNationYStart, width, height, "footprint", null, nation.getDateRange().getMaskName()));
		stringBuilder.append(rectangle(nation.getWikipediaId(), x, nextNationYStart, width, height, nation.getNameCamelCase(), null, nation.getDateRange().getMaskName()));
		stringBuilder.append("<title>");
		stringBuilder.append(nation.getAnnotation());
		stringBuilder.append("</title>");
		stringBuilder.append("</a></g>\n");
		System.out.println("  added nation "+nation);
		
		for (NamedEvent conflict : nation.getConflicts()) {
			stringBuilder.append("<g><a xlink:href=\""+conflict.getWikipediaLink()+"\" target=\"_blank\">");
			x = yearMapping(conflict.getDateRange().getStartYear());
			width = (int) (slope() * conflict.getDateRange().getDuration());
			stringBuilder.append(rectangle(conflict.getWikipediaId(), x, nextNationYStart, width, height, "conflict", null, conflict.getDateRange().getMaskName()));
			stringBuilder.append("<title>");
			stringBuilder.append(conflict.getAnnotation());
			stringBuilder.append("</title>");
			stringBuilder.append("</a></g>\n");
		}
		
		lastPersonInGroup = null;
		dynastyStart = nextNationYStart;
		nextPersonYStart = dynastyStart + lifetimeYDiff;
		for (Person person : nation.getPeople()) {
			getPersonSVG(person, nation.getNameCamelCase(), "fill-opacity:0.8", stringBuilder);
		}
		
		lastNationYStart = nextNationYStart;
		nextNationYStart += height+ lifetimeYDiff;
		if (maxLifetimeYEnd < nextNationYStart) {
			maxLifetimeYEnd = nextNationYStart;
		}
		lastNation = nation;
	}

	
	
	private static final String XML_SOURCE = "./RomanHistory.xml";
	private static final String HTML_TARGET = "./RomanHistoryTimeline.html";
	private static final String SVG_TARGET = "./RomanHistoryTimeline.svg";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		int startYear = -500;
//		int endYear = 500;
//		Timeline timeline = new Timeline("Ancient Rome", new DateRange(startYear, endYear));
//		DynastyGroup group1 = new DynastyGroup();
//		group1.setName("groupName1");
//		group1.setyStart(100);
//		Dynasty dynasty1 = new Dynasty();
//		dynasty1.setName("dynastyName1");
//		dynasty1.setyStart(150);
//		Person person1 = new Person();
//		person1.setName("Person Name");
//		person1.setLifespan(new DateRange(100, 200));
//		Title title1 = new Title();
//		title1.setName("title1 name");
//		title1.setReign(new DateRange(1, 2));
//		person1.addTitles(title1);
//		dynasty1.addPerson(person1);
//		group1.addDynasty(dynasty1);
//		Dynasty dynasty2 = new Dynasty();
//		dynasty2.setName("dynastyName2");
//		dynasty2.setyStart(150);
//		group1.addDynasty(dynasty2);
//		timeline.addDynastyGroup(group1);
//		DynastyGroup group2 = new DynastyGroup();
//		group2.setName("groupName2");
//		group2.setyStart(100);
//		timeline.addDynastyGroup(group2);
//		
//		System.out.println(timeline);
		
	    // create JAXB context and instantiate marshaller
	    JAXBContext context;
		try {
			context = JAXBContext.newInstance(Timeline.class);
//		    Marshaller m = context.createMarshaller();
//		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//	
//		    // Write to System.out
//		    m.marshal(timeline, System.out);
//	
//		    // Write to File
//		    m.marshal(timeline, new File("./RomanHistoryGen.xml"));
//	
//		    // get variables from our xml file, created before
//		    System.out.println();
		    System.out.println("Output from our XML File: ");
		    Unmarshaller um = context.createUnmarshaller();
		    Timeline timeline2 = (Timeline) um.unmarshal(new FileReader(XML_SOURCE));

			File htmlFile = new File(HTML_TARGET);
			FileWriter fileWriter = new FileWriter(htmlFile);
			fileWriter.append(timeline2.toHTML());
			fileWriter.flush();
			fileWriter.close();

			File svgFile = new File(SVG_TARGET);
			fileWriter = new FileWriter(svgFile);
			fileWriter.append(timeline2.toSVG());
			fileWriter.flush();
			fileWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

	private String getGradientDefs() {
		String result = FileUtilities.getFileContents("./gradientDefs.txt");
		return result;
	}
	
}
