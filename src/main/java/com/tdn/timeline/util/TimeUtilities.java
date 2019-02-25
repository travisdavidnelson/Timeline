package com.tdn.timeline.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtilities {
	public static String BC = "\\s*BC";
	public static String DATE_REGEX = "(\\d+(/)?)+("+BC+")?";
	public static Pattern DIGITS_PATTERN = Pattern.compile("(\\d+)");
	
	public static TimelineInstant getInstant(int year) {
		return getInstant(year, 1, 1);
	}
	public static TimelineInstant getInstant(int year, int month, int day) {
		TimelineInstant result = null;
		int instantYear = year;
		if (year < 0) {
			instantYear++;
		}
		StringBuilder dateStringBuilder = new StringBuilder();
		if (instantYear < 0) {
			dateStringBuilder.append("-");
		}
		dateStringBuilder.append(padYearWithZeros(instantYear));
		dateStringBuilder.append(Math.abs(instantYear));
		dateStringBuilder.append("-");
		dateStringBuilder.append(padMonthWithZeros(month));
		dateStringBuilder.append(Math.abs(month));
		dateStringBuilder.append("-");
		dateStringBuilder.append(padMonthWithZeros(day));
		dateStringBuilder.append(Math.abs(day));
		dateStringBuilder.append("T00:00:00Z");
		String dateString = dateStringBuilder.toString();
		Instant instant = Instant.parse(dateString);
		result = new TimelineInstant(""+year, instant, year);
		return result;
	}
	
	public static TimelineInstant getInstant(String input) {
		TimelineInstant result = null;
		if (input.matches(DATE_REGEX)) {
			YearMonthDay yearMonthDay = extractYearMonthDay(input);
			int instantYear = yearMonthDay.year;
			boolean isBC = input.toLowerCase().contains("bc");
			if (isBC) {
				yearMonthDay.year = 0 - yearMonthDay.year;
				instantYear = yearMonthDay.year + 1;
			}
			StringBuilder dateStringBuilder = new StringBuilder();
			if (instantYear < 0) {
				dateStringBuilder.append("-");
			}
			dateStringBuilder.append(padYearWithZeros(instantYear));
			dateStringBuilder.append(Math.abs(instantYear));
			dateStringBuilder.append("-");
			dateStringBuilder.append(padMonthWithZeros(yearMonthDay.month));
			dateStringBuilder.append(Math.abs(yearMonthDay.month));
			dateStringBuilder.append("-");
			dateStringBuilder.append(padMonthWithZeros(yearMonthDay.day));
			dateStringBuilder.append(Math.abs(yearMonthDay.day));
			dateStringBuilder.append("T00:00:00Z");
			String dateString = dateStringBuilder.toString();
			Instant instant = Instant.parse(dateString);
			result = new TimelineInstant(input, instant, yearMonthDay.year);
		}
		return result;
	}
	
	public static String padYearWithZeros(int input) {
		String result = "";
		int inputAbs = Math.abs(input);
		if (inputAbs < 10) {
			result = "000";
		} else if (inputAbs < 100) {
			result = "00";
		} else if (inputAbs < 1000) {
			result = "0";
		}
		return result;
	}
	
	public static String padMonthWithZeros(int input) {
		String result = "";
		int inputAbs = Math.abs(input);
		if (inputAbs < 10) {
			result = "0";
		}
		return result;
	}
	
	public static List<String> extractNumericsAsStrings(String input) {
		List<String> result = new ArrayList<>();
		Matcher matcher = DIGITS_PATTERN.matcher(input);
		while(matcher.find()) {
			result.add(matcher.group());
		}
		return result;
	}
	
	public static YearMonthDay extractYearMonthDay(String input) {
		return new YearMonthDay(extractNumericsAsStrings(input));
	}
}

class YearMonthDay {
	int year;
	int month = 1;
	int day = 1;
	
	YearMonthDay(List<String> strings) {
		if (strings.size() == 1) {
			year = Integer.parseInt(strings.get(0));
		} else if (strings.size() == 2) {
			month = Integer.parseInt(strings.get(0));
			year = Integer.parseInt(strings.get(1));
		} else if (strings.size() == 3) {
			month = Integer.parseInt(strings.get(0));
			day = Integer.parseInt(strings.get(1));
			year = Integer.parseInt(strings.get(2));
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(TimeUtilities.padYearWithZeros(year));
		result.append(year);
		result.append(TimeUtilities.padMonthWithZeros(month));
		result.append(month);
		result.append(TimeUtilities.padMonthWithZeros(day));
		result.append(day);
		return result.toString();
	}
}