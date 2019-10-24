package io.jenkins.plugins.simplenotification;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;

import java.util.ArrayList;
import java.util.List;

@Extension
public class SimpleNotification extends GlobalConfiguration {

    private List<Endpoint> endpoints;

    public static SimpleNotification get() {
        return GlobalConfiguration.all().get(SimpleNotification.class);
    }

    public SimpleNotification() {
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
