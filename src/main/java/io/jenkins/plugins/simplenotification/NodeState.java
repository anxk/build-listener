package io.jenkins.plugins.simplenotification;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import hudson.model.Computer;
import hudson.slaves.OfflineCause;

public class NodeState implements ItemState {

    private String id;
    private String name;
    private Map<String, String> description;

    public NodeState(Computer c) {
        setId();
        setName(c);
        setDescription(c);
    }

    public NodeState(Computer c, OfflineCause cause) {
        setId();
        setName(c);
        setDescription(c, cause);
    }

    public String getId() {
        return id;
    }

    private void setId() {
        this.id =  UUID.randomUUID().toString();
    }

    public Map<String, String> getDescription() {
        return description;
    }

    private void setDescription(Computer c) {
        Map<String, String> description = new HashMap<>();
        description.put("name", c.getName());
        description.put("uri", c.getUrl());
        this.description = description;
    }

    private void setDescription(Computer c, OfflineCause cause) {
        Map<String, String> description = new HashMap<>();
        description.put("name", c.getName());
        description.put("uri", c.getUrl());
        description.put("cause", c.getOfflineCauseReason());
        this.description = description;
    }

    private void setName(Computer c) {
        this.name = c.getName();
    }

    public String getName() {
        return name;
    }
}