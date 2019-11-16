package io.jenkins.plugins.nodenotification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.common.base.Splitter;

import hudson.model.Computer;
import hudson.tasks.Mailer;
import io.jenkins.plugins.nodenotification.NotificationNodeProperty.Entry;
import jenkins.model.JenkinsLocationConfiguration;

public class MailPublisher {

    private static Logger LOGGER = Logger.getLogger(MailPublisher.class.getName());

    public void publish(String cause, Computer c, List<NotificationNodeProperty.Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getType() == "email") {
                for (Endpoint endpoint : NodeNotificationConfiguration.get().getEndpoints()) {
                    if (endpoint.getType() == "email") {
                        _publish(cause, c, entry);
                    }
                }
            }
        }
    }

    public void _publish(String cause, Computer c, NotificationNodeProperty.Entry entry) {
        Session session = new Mailer.DescriptorImpl().createSession();
        Message message = new MimeMessage(session);
        try {
            message.setFrom(createFrom());
            message.setRecipients(Message.RecipientType.TO, createRecipients(entry.getRecipients(), c));
            message.setSubject(createSubject(cause, c, entry));
            message.setText(createBody(cause, c, entry));
        } catch (MessagingException | IllegalStateException  e) {
            LOGGER.log(Level.WARNING, "Failed to build message for " + c.getName(), e);
        }
        try {
            Transport.send(message, message.getAllRecipients());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to send email for " + c.getName(), e);
        }
    }

    public InternetAddress createFrom() {
        JenkinsLocationConfiguration jlc = JenkinsLocationConfiguration.get();
        InternetAddress from = new InternetAddress();
        from.setAddress(jlc.getAdminAddress());
        return from;
    }

    public String createSubject(String cause, Computer c, NotificationNodeProperty.Entry entry) {
        StringBuilder subject = new StringBuilder();
        subject.append("Attention! " + c.getName() + " is offline");
        return subject.toString();
    }

    public String createBody(String cause, Computer c, NotificationNodeProperty.Entry entry) {
        StringBuilder message = new StringBuilder();
        message.append("Offline Cause: " + cause);
        message.append("\nNode Name: " + c.getName());
        message.append("\nNode URL: " + c.getUrl());
        message.append("\nMessage: " + entry.getMessage());
        return message.toString();
    }

    public InternetAddress[] createRecipients(String recipients, Computer c) {
        List<InternetAddress> to = new ArrayList<InternetAddress>();
        Iterator<String> iter = Splitter.on(Pattern.compile("[,; ]"))
            .omitEmptyStrings()
            .trimResults()
            .split(recipients)
            .iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            try {
                InternetAddress address = new InternetAddress(str);
                to.add(address);
            } catch (AddressException
                    | UnsupportedOperationException
                    | ClassCastException
                    | NullPointerException
                    | IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Failed to add address " + str + " to recipients for node " + c.getName(), e);
                continue;
            }
        }
        InternetAddress[] addresses = new InternetAddress[to.size()];
        return to.toArray(addresses);
    }

}