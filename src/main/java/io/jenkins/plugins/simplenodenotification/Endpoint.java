package io.jenkins.plugins.simplenodenotification;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;

public class Endpoint extends AbstractDescribableImpl<Endpoint>{

    private String url;
    private String regex;

    @DataBoundConstructor
    public Endpoint(String url, String regex) {
        this.url = url;
        this.regex = regex;
    }

    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @DataBoundSetter
    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<Endpoint> {

        @Override
        public String getDisplayName() {
            return "";
        }

    }

}
