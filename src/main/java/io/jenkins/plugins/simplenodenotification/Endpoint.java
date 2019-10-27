package io.jenkins.plugins.simplenodenotification;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class Endpoint {

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

}
