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
