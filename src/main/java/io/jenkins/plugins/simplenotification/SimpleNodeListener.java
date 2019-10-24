package io.jenkins.plugins.simplenotification;

import java.util.List;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.TaskListener;
import hudson.slaves.ComputerListener;
import hudson.slaves.OfflineCause;
import hudson.slaves.SlaveComputer;

@Extension
public class SimpleNodeListener extends ComputerListener {

    @Override
    public void onOffline(Computer c, OfflineCause cause) {
        Event event = new Event(new NodeState(c, cause));
		List<Endpoint> endpoints = SimpleNotification.get().getEndpoints();
		for (Endpoint endpoint : endpoints) {
			String url = endpoint.getUrl();
			if (endpoint.isEnableRegex()) {
				if (Utils.isRegexMatch(endpoint.getInclude(), event.getState().getName())) {
					if (!Utils.isRegexMatch(endpoint.getExclude(), event.getState().getName())) {
						new HTTPPublisher().publish(url, event);
					}
				}
			}
			if (!endpoint.isEnableRegex()) {
				if (Utils.isGlobMatch(endpoint.getInclude(), event.getState().getName())) {
					if (!Utils.isGlobMatch(endpoint.getExclude(), event.getState().getName())) {
						new HTTPPublisher().publish(url, event);
					}
				}
			}
		}
    }

    @Override
    public void onOnline(Computer c, TaskListener listener) {
		if (c instanceof SlaveComputer) {
			Event event = new Event(new NodeState(c));
			List<Endpoint> endpoints = SimpleNotification.get().getEndpoints();
			for (Endpoint endpoint : endpoints) {
				String url = endpoint.getUrl();
				if (endpoint.isEnableRegex()) {
					if (Utils.isRegexMatch(endpoint.getInclude(), event.getState().getName())) {
						if (!Utils.isRegexMatch(endpoint.getExclude(), event.getState().getName())) {
							new HTTPPublisher().publish(url, event);
						}
					}
				}
				if (!endpoint.isEnableRegex()) {
					if (Utils.isGlobMatch(endpoint.getInclude(), event.getState().getName())) {
						if (!Utils.isGlobMatch(endpoint.getExclude(), event.getState().getName())) {
							new HTTPPublisher().publish(url, event);
						}
					}
				}
			}
		}
    }
}