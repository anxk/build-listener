package io.jenkins.plugins.globallistener;

import java.util.List;

import hudson.Extension;
import hudson.model.listeners.RunListener;
import hudson.model.Run;
import hudson.model.TaskListener;

@Extension
public class GlobalRunListener extends RunListener<Run<?, ?>> {

	/**
	 * TODO: should filter event.
	 */
	@Override
	public void onStarted(Run<?, ?> run, TaskListener listener) {
		Event event = new Event(new RunState(run));
		List<Channel> channels = GlobalListenerConfiguration.get().getChannels();
		for (Channel channel : channels) {
			String url = channel.getUrl();
			new HTTPPublisher().publish(url, event);
		}
	}

	/**
	 * TODO: should filter event.
	 */
	@Override
	public void onCompleted(Run<?, ?> run, TaskListener listener) {
		Event event = new Event(new RunState(run));
		List<Channel> channels = GlobalListenerConfiguration.get().getChannels();
		for (Channel channel : channels) {
			String url = channel.getUrl();
			new HTTPPublisher().publish(url, event);
		}
	}
}
