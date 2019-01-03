package com.tdn.timeline2;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TimespanDeserializer implements JsonDeserializer<Timespan> {

	@Override
	public Timespan deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonData = null;
		if (JsonObject.class.isInstance(json)) {
			jsonData = json.getAsJsonObject();
		}
		assert jsonData != null;
		int startYear = jsonData.get("startYear").getAsInt();
		int endYear = jsonData.get("endYear").getAsInt();
		Timespan result = new Timespan();
		result.setStartYear(startYear);
		result.setEndYear(endYear);
		result.setStartYearApproximate(jsonData.has("startYearApproximate"));
		result.setEndYearApproximate(jsonData.has("endYearApproximate"));
		return result;
	}

}
