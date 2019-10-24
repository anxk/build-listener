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

    public static void notify(Event event) {
        List<Endpoint> endpoints = SimpleNotification.get().getEndpoints();
		for (Endpoint endpoint : endpoints) {
			if (isMatch(event, endpoint)) {
                                String url = endpoint.getUrl();
				new HTTPPublisher().publish(url, event);
			}
		}
    }

    private static Boolean isMatch(Event e, Endpoint p) {
        if (e.getState() instanceof RunState && p.getItemType() == "Agent" ||
            e.getState() instanceof NodeState && p.getItemType() == "Job") {
            return false;
        }
		if (p.isEnableRegex()) {
			if (Utils.isRegexMatch(p.getInclude(), e.getState().getName())) {
				if (!Utils.isRegexMatch(p.getExclude(), e.getState().getName())) {
					return true;
				}
			}
		} else {
			if (Utils.isGlobMatch(p.getInclude(), e.getState().getName())) {
				if (!Utils.isGlobMatch(p.getExclude(), e.getState().getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
