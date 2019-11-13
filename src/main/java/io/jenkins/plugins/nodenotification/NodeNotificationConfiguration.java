package io.jenkins.plugins.nodenotification;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;

import java.util.ArrayList;
import java.util.List;

@Extension
public class NodeNotificationConfiguration extends GlobalConfiguration {

    private List<Endpoint> endpoints;

    public static NodeNotificationConfiguration get() {
        return GlobalConfiguration.all().get(NodeNotificationConfiguration.class);
    }

    public NodeNotificationConfiguration() {
        this.endpoints = new ArrayList<Endpoint>();
        load();
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
        save();
    }
    
}
