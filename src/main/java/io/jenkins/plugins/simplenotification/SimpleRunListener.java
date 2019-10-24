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
		SimpleNotification.notify(event);
	}

	@Override
	public void onCompleted(Run<?, ?> run, TaskListener listener) {
		Event event = new Event(new RunState(run));
		SimpleNotification.notify(event);
	}
}
