package io.jenkins.plugins.globallistener;

import net.sf.json.JSONObject; 

public class Event {

    private long timestamp;
    private String name;
    private String state;

    public Event(String name, String state) {
        this.timestamp = System.currentTimeMillis();
        this.name = name;
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp; 
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String toJsonString() {
        return JSONObject.fromObject(this).toString();
    }
}