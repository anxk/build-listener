package io.jenkins.plugins.globallistener;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class Channel {
    private String url;
    private String include;
    private String exclude;

    @DataBoundConstructor
    public Channel(String url) {
        this.url = url;
    }

    @DataBoundSetter
    public void setInclude(String include) {
        this.include = include;
    }

    @DataBoundSetter
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getUrl() {
        return url;
    }

    public String getInclude() {
        return include;
    }

    public String getExclude() {
        return exclude;
    }
}