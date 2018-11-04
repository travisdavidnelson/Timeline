package com.tdn.timeline.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.tdn.util.FileUtilities;

public class DateRangeTest {

	@Test
	public void test_deserialization() {
		String json = FileUtilities.getFileContents("src/main/resources/DateRange.json");
		Gson gson = new Gson();
		DateRange dateRange = gson.fromJson(json, DateRange.class);
		System.out.println(dateRange);
	}

}
