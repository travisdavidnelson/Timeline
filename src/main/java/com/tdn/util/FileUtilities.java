package com.tdn.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilities {

	public static String getFileContents(String filename) throws IOException {
		StringBuilder result = new StringBuilder();
		
		File file = new File(filename);
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
			throw new FileNotFoundException(filename);
		}
		
		return result.toString();
	}
	
}
