package com.tdn.timeline;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tdn.timeline.util.TimeUtilities;
import com.tdn.timeline.util.TimelineInstant;

public class TimespanDeserializer implements JsonDeserializer<Timespan> {

	@Override
	public Timespan deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonData = null;
		if (JsonObject.class.isInstance(json)) {
			jsonData = json.getAsJsonObject();
		}
		assert jsonData != null;
		Timespan result = new Timespan();
		String startString = jsonData.get("start").getAsString();
		TimelineInstant start = TimeUtilities.getInstant(startString);
		result.setStart(start);
		if (jsonData.has("end")) {
			String endString = jsonData.get("end").getAsString();
			TimelineInstant end = TimeUtilities.getInstant(endString);
			result.setEnd(end);
		}
		if (jsonData.has("duration")) {
			String durationString = jsonData.get("duration").getAsString();
			long durationInDays = 0;
			if (durationString.contains("y")) {
				String durationInDaysString = durationString.substring(0, durationString.length()-1);
				durationInDays = Math.round(Long.parseLong(durationInDaysString) * 365.25);
			} else if (durationString.contains("m")) {
				String durationInDaysString = durationString.substring(0, durationString.length()-1);
				durationInDays = Long.parseLong(durationInDaysString) * 30;
			} else if (durationString.contains("d")) {
				String durationInDaysString = durationString.substring(0, durationString.length()-1);
				durationInDays = Long.parseLong(durationInDaysString);
			}
			result.setDuration(durationInDays);
		}
		result.setStartYearApproximate(jsonData.has("startYearApproximate"));
		result.setEndYearApproximate(jsonData.has("endYearApproximate"));
		return result;
	}

}
