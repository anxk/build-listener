package io.jenkins.plugins.simplenodenotification;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Extension
public class SimpleNodeNotification extends GlobalConfiguration {

    private List<Endpoint> endpoints;

    public static SimpleNodeNotification get() {
        return GlobalConfiguration.all().get(SimpleNodeNotification.class);
    }

    public SimpleNodeNotification() {
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

    public static void boardcast(Event event) {
		for (Endpoint endpoint : SimpleNodeNotification.get().getEndpoints()) {
			if (isMatch(event, endpoint)) {
				Publisher.publish(endpoint, event);
			}
		}
    }

    public static Boolean isMatch(Event e, Endpoint p) {
        if (p.getRegex().equals("") 
            || Pattern.matches(p.getRegex(), e.getState().getName())) {
            return true;
        }
        return false;
    }
    
}
