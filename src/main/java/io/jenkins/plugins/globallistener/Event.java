package io.jenkins.plugins.globallistener;

import net.sf.json.JSONObject; 

public class Event {

    private long timestamp;
    private State state;

    public Event(State state) {
        this.timestamp = System.currentTimeMillis();
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp; 
    }

    public State getState() {
        return state;
    }

    public String toJsonString() {
        return JSONObject.fromObject(this).toString();
    }
}