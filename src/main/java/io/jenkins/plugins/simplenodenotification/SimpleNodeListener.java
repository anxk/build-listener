package io.jenkins.plugins.simplenodenotification;

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
        if (c instanceof SlaveComputer) {
            Event event = new Event(new NodeState(c));
            SimpleNodeNotification.boardcast(event);
        }
    }

    @Override
    public void onOnline(Computer c, TaskListener listener) {
		if (c instanceof SlaveComputer) {
			Event event = new Event(new NodeState(c));
			SimpleNodeNotification.boardcast(event);
		}
    }
    
}