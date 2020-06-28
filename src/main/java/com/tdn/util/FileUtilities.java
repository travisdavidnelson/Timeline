package com.tdn.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtilities {

	public static File getResourceAsFile(String filename) {
		ClassLoader classLoader = FileUtilities.class.getClassLoader();
		URL resource = classLoader.getResource(filename);
		return new File(resource.getFile());
	}

	public static String getFileContents(String filename) throws IOException {
		return getFileContents(new File(filename));
	}
	public static String getFileContents(File file) throws IOException {
		StringBuilder result = new StringBuilder();
		
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file));)
			{
				String nextLine = reader.readLine();
				while (nextLine != null) {
					result.append(nextLine);
					result.append("\n");
					nextLine = reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else {
			System.err.println("File " + file + " does not exist.");
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		
		return result.toString();
	}
	
	static Pattern numberWithSignPattern = Pattern.compile("(-?\\d+)");
	static Pattern numberPattern = Pattern.compile("(\\d+)");
	
	public static void main(String[] args) {
		try {
			String filename = "/Users/travis/dev/workspace/Timeline/src/main/resources/RomanHistory.json";
			File file = new File(filename);
			if (file.exists()) {
				String filename2 = "/Users/travis/dev/workspace/Timeline/src/main/resources/RomanHistoryX.json";
				File file2 = new File(filename2);
				file2.createNewFile();
				try (BufferedReader reader = new BufferedReader(new FileReader(file));
						BufferedWriter writer = new BufferedWriter(new FileWriter(file2)))
				{
					String nextLine = reader.readLine();
					while (nextLine != null) {
						String nextLineTransformed = transformLine(nextLine);
						writer.write(nextLineTransformed);
						writer.write("\n");
						nextLine = reader.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			else {
				System.err.println("File " + file + " does not exist.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String transformLine(String line) {
		String result = line;
		if (result.contains("startYear") || result.contains("endYear")) {
			result = line.replaceAll("\"startYear\"", "\"start\"").replaceAll("\"endYear\"", "\"end\"");
			Matcher numberWithSignPatternMatcher = numberWithSignPattern.matcher(result);
			if (numberWithSignPatternMatcher.find()) {
				String numberWithSign = numberWithSignPatternMatcher.group();
				Matcher numberPatternMatcher = numberPattern.matcher(numberWithSign);
				numberPatternMatcher.find();
				String number = numberPatternMatcher.group();
				StringBuilder numberReplacment = new StringBuilder();
				numberReplacment.append("\"");
				numberReplacment.append(number);
				if (numberWithSign.startsWith("-")) {
					numberReplacment.append(" BC");
				}
				numberReplacment.append("\"");
				result = result.replaceAll(numberWithSign, numberReplacment.toString());
			}
		}
		return result;
	}
}
