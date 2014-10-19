package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtilities {

	public static String getFileContents(String filename) {
		StringBuilder result = new StringBuilder();
		
		File file = new File(filename);
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
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
		
		return result.toString();
	}
	
}
