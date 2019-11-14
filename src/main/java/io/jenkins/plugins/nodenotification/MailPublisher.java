package io.jenkins.plugins.nodenotification;

import java.util.List;
import java.util.logging.Logger;

import io.jenkins.plugins.nodenotification.NotificationNodeProperty.Entry;

public class MailPublisher {

    private static Logger LOGGER = Logger.getLogger(MailPublisher.class.getName());

    public static void publish(String cause, String nodeInfo, List<NotificationNodeProperty.Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getType() == "email") {
                String message = build(cause, nodeInfo, entry);
                for (Endpoint endpoint: NodeNotificationConfiguration.get().getEndpoints()) {
                    if (endpoint.getType() == "email") {
                        _publish(endpoint.getUrl(), message);
                    }
                }
            }
        }
    }

    public static String build(String cause, String nodeInfo, NotificationNodeProperty.Entry entry) {
        StringBuilder message = new StringBuilder();
        message.append("Offline Cause: " + cause);
        message.append("\nNode Info: " + nodeInfo);
        message.append("\nMessage: " + entry.getMessage());
        return message.toString();
    }
    
    public static void _publish(String url, String message) {
        LOGGER.info("Send email successfull");
    }

}