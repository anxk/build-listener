package io.jenkins.plugins.nodenotification;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.Node;
import hudson.slaves.ComputerListener;
import hudson.slaves.OfflineCause;
import hudson.slaves.SlaveComputer;

@Extension
public class OfflineMonitor extends ComputerListener {

    @Override
    public void onOffline(Computer c, OfflineCause cause) {
        if (c instanceof SlaveComputer) {
            Node node = c.getNode();
            if (node != null) {
                NotificationNodeProperty p = node.getNodeProperty(NotificationNodeProperty.class);
                if(p != null) {
                    new MailPublisher().publish(cause, c, p.getEntrys());
                    new SMSPublisher().publish(cause, c, p.getEntrys());
                }
            }
        }
    }

}