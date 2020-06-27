package com.tdn.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdn.timeline.Timeline;
import com.tdn.timeline.Timespan;
import com.tdn.timeline.TimespanDeserializer;

import java.io.File;
import java.io.IOException;

public class ModelBuilder<T> {

    public ModelBuilder() {

    }

    public T populateFromFile(Class<T> type, File file) throws IOException {
        T result = null;
        String json = FileUtilities.getFileContents(file);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Timespan.class, new TimespanDeserializer())
                .create();
        result = gson.fromJson(json, type);
        return result;
    }
}
