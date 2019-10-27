package io.jenkins.plugins.simplenodenotification;

import java.util.UUID;

import net.sf.json.JSONObject; 

public class Event {

    private String eventId;
    private long timestamp;
    private NodeState state;

    public Event(NodeState state) {
        setEventId();
        setTimestamp();
        setState(state);
    }

    public void setEventId() {
        this.eventId = UUID.randomUUID().toString();
    }

    public String getEventId() {
        return eventId;
    }

    public void setTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp; 
    }

    public void setState(NodeState state) {
        this.state = state;
    }
    public NodeState getState() {
        return state;
    }

    public JSONObject toJson() {
        return JSONObject.fromObject(this);
    }
}