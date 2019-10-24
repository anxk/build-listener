package io.jenkins.plugins.simplenotification;

import java.util.UUID;

import net.sf.json.JSONObject; 

public class Event {

    private String id;
    private long timestamp;
    private ItemState state;

    public Event(ItemState state) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp; 
    }

    public ItemState getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public String toJsonString() {
        return JSONObject.fromObject(this).toString();
    }
}