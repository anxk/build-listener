package io.jenkins.plugins.nodenotification;

import java.util.List;

public class Publisher {

    public static void publish(String cause, String nodeInfo, List<NotificationNodeProperty.Entry> entrys) {
        MailPublisher.publish(cause, nodeInfo, entrys);
        SMSPublisher.publish(cause, nodeInfo, entrys);
    }
    
}
