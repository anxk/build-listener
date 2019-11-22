package io.jenkins.plugins.nodenotification;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;

public class PropertyEntry extends AbstractDescribableImpl<PropertyEntry> {

    private String type;
    private String recipients;
    private String message;

    @DataBoundConstructor
    public PropertyEntry(String type, String recipients, String message) {
        this.type = type;
        this.recipients = recipients;
        this.message = message;
    }

    @DataBoundSetter
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @DataBoundSetter
    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getRecipients() {
        return recipients;
    }

    @DataBoundSetter
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<PropertyEntry> {
        
        public String getDisplayName() {
            return "";
        }

        public ListBoxModel doFillTypeItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("SMS", "sms");
            items.add("Email", "email");;
            return items;
        }

    }

}