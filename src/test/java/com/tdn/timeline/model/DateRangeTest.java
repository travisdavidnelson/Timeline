package com.tdn.timeline.model;

import static org.junit.Assert.*;

import java.lang.reflect.Type;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tdn.util.FileUtilities;

public class DateRangeTest {

	@Test
	public void test_deserialization() {
		String json = FileUtilities.getFileContents("src/main/resources/DateRange.json");
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(DateRange.class, new DateRangeDeserializer())
				.create();
		DateRange dateRange = gson.fromJson(json, DateRange.class);
		System.out.println(dateRange);
	}

}

class DateRangeDeserializer implements JsonDeserializer<DateRange> {

	@Override
	public DateRange deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonData = null;
		if (JsonObject.class.isInstance(jsonElement)) {
			jsonData = jsonElement.getAsJsonObject();
		}
		assert jsonData != null;
		int startYear = jsonData.get("startYear").getAsInt();
		int endYear = jsonData.get("endYear").getAsInt();
		
		DateRange result = new DateRange(startYear, endYear);
		result.setStartYearApproximate(jsonData.has("startYearApproximate"));
		result.setEndYearApproximate(jsonData.has("endYearApproximate"));
		return result;
	}
	
}
