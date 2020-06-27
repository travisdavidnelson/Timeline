package com.tdn.timeline;

public class Channel {

    private String name;
    private String resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Channel ");
        result.append(name);
        result.append(" ");
        result.append(resource);
        return result.toString();
    }

}
