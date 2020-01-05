package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

public class Timeline extends TimelineEvent {
	private String id = null;
	private String author = "Travis David Nelson";
	private TimelineConfig config = null;

	private List<TimelineLayer> layers;

	public Timeline() {
		super();
		config = new TimelineConfig();

		layers = new ArrayList<>();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public TimelineConfig getConfig() {
		return config;
	}
	public void setConfig(TimelineConfig config) {
		this.config = config;
	}

	public List<TimelineLayer> getLayers() {
		return layers;
	}
	public void setLayers(List<TimelineLayer> layers) {
		this.layers = layers;
	}
	public void addLayer(TimelineLayer layer) {
		this.layers.add(layer);
	}

}
