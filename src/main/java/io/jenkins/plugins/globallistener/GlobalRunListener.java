package io.jenkins.plugins.globallistener;

import hudson.Extension;
import hudson.model.listeners.RunListener;
import hudson.model.Run;
import hudson.model.TaskListener;

@Extension
public class GlobalRunListener extends RunListener<Run<?, ?>> {

	@Override
	public void onStarted(Run<?, ?> run, TaskListener listener) {
		String url = GlobalListenerConfiguration.get().getUrl();
		Event event = new Event(new RunState(run));
		new HTTPPublisher().publish(url, event);
	}

	@Override
	public void onCompleted(Run<?, ?> run, TaskListener listener) {
		String url = GlobalListenerConfiguration.get().getUrl();
		Event event = new Event(new RunState(run));
		new HTTPPublisher().publish(url, event);
	}
}
