package com.tdn.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdn.timeline.Timespan;
import com.tdn.timeline.TimespanDeserializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ModelBuilder<T> {

    public ModelBuilder() {

    }

    public T deserialize(Class<T> type, String filename) throws IOException {
        T result = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);
        InputStreamReader reader = new InputStreamReader(is);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Timespan.class, new TimespanDeserializer())
                .create();
        result = gson.fromJson(reader, type);
        return result;
    }
    
    public T deserialize(Class<T> type, File file) throws IOException {
        String json = FileUtilities.getFileContents(file);
        return fromString(type, json);
    }

    public T fromString(Class<T> type, String json) throws IOException {
        T result = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Timespan.class, new TimespanDeserializer())
                .create();
        result = gson.fromJson(json, type);
        return result;
    }

}
