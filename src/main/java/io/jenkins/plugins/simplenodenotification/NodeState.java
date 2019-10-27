package io.jenkins.plugins.simplenodenotification;

import hudson.model.Computer;

public class NodeState {

    private String name;
    private String url;
    private Boolean online;


    public NodeState(Computer c) {
        setName(c);
        setUrl(c);
        setOnline(c);
    }

    public void setUrl(Computer c) {
        this.url = c.getUrl();
    }

    public String getUrl() {
        return url;
    }

    public void setName(Computer c) {
        this.name = c.getName();
    }

    public String getName() {
        return name;
    }

    public void setOnline(Computer c) {
        this.online = c.isOnline();
    }

    public Boolean getOnline() {
        return online;
    }

}