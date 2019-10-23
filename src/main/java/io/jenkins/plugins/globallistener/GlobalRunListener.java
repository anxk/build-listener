package io.jenkins.plugins.globallistener;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import hudson.model.Result;

@Extension
public class RunListener extends RunListener<Run<?, ?>> {

	@Override
	public void onStarted(Run<?, ?> run, TaskListener listener) {
		String url = GlobalListenerConfiguration.get().getUrl();
		Event event = new Event(run.getDisplayName(), "Started");
		new HTTPPublisher().publish(url, event);
	}

	@Override
	public void onCompleted(Run<?, ?> run, TaskListener listener) {
		String url = GlobalListenerConfiguration.get().getUrl();
		String resultString = "";
		Result result = run.getResult();
		if (result != null) {
			resultString = result.toString();
		}
		Event event = new Event(run.getDisplayName(), resultString);
		new HTTPPublisher().publish(url, event);
	}
}
