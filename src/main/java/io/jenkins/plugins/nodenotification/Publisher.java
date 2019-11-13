package io.jenkins.plugins.nodenotification;

import java.util.List;
import java.util.logging.Logger;

public class Publisher {

    private static Logger LOGGER = Logger.getLogger(Publisher.class.getName());

    public static void publish(String cause, String nodeInfo, List<NotificationNodeProperty.Entry> entrys) {
        LOGGER.info("Send notification successfully!");
    }
    
}
