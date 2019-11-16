package io.jenkins.plugins.nodenotification;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;

public class Endpoint extends AbstractDescribableImpl<Endpoint> {

    private String type;
    private String url;
    private String playloadTemplate;

    @DataBoundConstructor
    public Endpoint(String type, String url, String playloadTemplate) {
        this.type = type;
        this.url = url;
        this.playloadTemplate = playloadTemplate;
    }

    @DataBoundSetter
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
    public static final class DescriptorImpl extends Descriptor<Endpoint> {

        @Override
        public String getDisplayName() {
            return "";
        }

    }

}
