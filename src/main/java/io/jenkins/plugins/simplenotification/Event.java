package io.jenkins.plugins.simplenotification;

import net.sf.json.JSONObject; 

public class Event {

    private long timestamp;
    private ItemState state;

    public Event(ItemState state) {
        this.timestamp = System.currentTimeMillis();
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp; 
    }

    public ItemState getState() {
        return state;
    }

    public String toJsonString() {
        return JSONObject.fromObject(this).toString();
    }
}