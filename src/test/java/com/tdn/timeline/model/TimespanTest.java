package com.tdn.timeline.model;

import java.io.IOException;
import java.lang.reflect.Type;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tdn.timeline.Timespan;
import com.tdn.timeline.util.TimeUtilities;
import com.tdn.timeline.util.TimelineInstant;
import com.tdn.util.FileUtilities;

public class TimespanTest {

	@Test
	public void test_deserialization() {
		String json = null;
		try {
			json = FileUtilities.getFileContents("src/main/resources/DateRange.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Timespan.class, new DateRangeDeserializer())
				.create();
		Timespan dateRange = gson.fromJson(json, Timespan.class);
		System.out.println(dateRange);
	}

}

class DateRangeDeserializer implements JsonDeserializer<Timespan> {

	@Override
	public Timespan deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonData = null;
		if (JsonObject.class.isInstance(json)) {
			jsonData = json.getAsJsonObject();
		}
		assert jsonData != null;
		String startString = jsonData.get("start").getAsString();
		TimelineInstant start = TimeUtilities.getInstant(startString);
		String endString = jsonData.get("end").getAsString();
		TimelineInstant end = TimeUtilities.getInstant(endString);
		Timespan result = new Timespan();
		result.setStart(start);
		result.setEnd(end);
		result.setStartYearApproximate(jsonData.has("startYearApproximate"));
		result.setEndYearApproximate(jsonData.has("endYearApproximate"));
		return result;
	}
	
}
