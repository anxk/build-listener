package io.jenkins.plugins.nodenotification;

import java.io.IOException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class Entry extends AbstractDescribableImpl<Entry> {

    private String type;
    private String recipients;
    private String message;

    @DataBoundConstructor
    public Entry(String type, String recipients, String message) {
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
    public static class DescriptorImpl extends Descriptor<Entry> {
        
        public String getDisplayName() {
            return "";
        }

        public FormValidation doCheckType(@QueryParameter String value) throws IOException, ServletException {
            if (value.equals("sms") || value.equals("email")) {
                return FormValidation.ok();
            }
            return FormValidation.error("Please input valid type, sms or email.");
        }

    }

}