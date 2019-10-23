package io.jenkins.plugins.globallistener;

import java.util.logging.Logger;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import hudson.model.Result;

@Extension
public class GlobalRunListener extends RunListener<Run<?, ?>> {

	//Called when a build is started.
	@Override
	public void onStarted(Run<?, ?> run, TaskListener listener) {
		String name = run.getDisplayName();
		Logger logger = Logger.getLogger("startLogger");
		String label = GlobalListenerConfiguration.get().getLabel();
		logger.warning(label + " : " + name + " is starting");
	}

	//Called after a build is completed.
	@Override
	public void onCompleted(Run<?, ?> run, TaskListener listener) {
		String name = run.getDisplayName();
		String resultString = "";
		Result result = run.getResult();
		if (result != null) {
			resultString = result.toString();
		}
		Logger logger = Logger.getLogger("CompleteLogger");
		String label = GlobalListenerConfiguration.get().getLabel();
		logger.warning(label + " : " + name + " is completed with result: " + resultString);
	}
}
