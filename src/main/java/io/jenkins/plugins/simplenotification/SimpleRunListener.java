package io.jenkins.plugins.simplenotification;

import java.util.List;

import hudson.Extension;
import hudson.model.listeners.RunListener;
import hudson.model.Run;
import hudson.model.TaskListener;

@Extension
public class SimpleRunListener extends RunListener<Run<?, ?>> {

	@Override
	public void onStarted(Run<?, ?> run, TaskListener listener) {
		Event event = new Event(new RunState(run));
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
	public void onCompleted(Run<?, ?> run, TaskListener listener) {
		Event event = new Event(new RunState(run));
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
