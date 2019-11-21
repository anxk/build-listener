package io.jenkins.plugins.nodenotification;

import java.io.IOException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class SMSEndpoint extends AbstractEndpoint {

    private String url;
    private String playloadTemplate;

    @DataBoundConstructor
    public SMSEndpoint(String url, String playloadTemplate) {
        this.url = url;
        this.playloadTemplate = playloadTemplate;
    }

    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @DataBoundSetter
    public void setPlayloadTemplate(String playloadTemplate) {
        this.playloadTemplate = playloadTemplate;
    }

    public String getPlayloadTemplate() {
        return playloadTemplate;
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<AbstractEndpoint> {

        @Override
        public String getDisplayName() {
            return "SMS";
        }

        public FormValidation doCheckPlayloadTemplate(@QueryParameter String value) throws IOException, ServletException {
            try {
                JSONObject.fromObject(value);
            } catch (JSONException e) {
                return FormValidation.error("Please input valid json string.");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckUrl(@QueryParameter String value) throws IOException, ServletException {
            if (value.startsWith("http") || value.startsWith("https")) {
                return FormValidation.ok();
            }
            return FormValidation.error("Please input valid url.");
        }

    }

}
