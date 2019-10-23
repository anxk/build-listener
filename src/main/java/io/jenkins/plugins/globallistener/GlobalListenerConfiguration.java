package io.jenkins.plugins.globallistener;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;


@Extension
public class GlobalListenerConfiguration extends GlobalConfiguration {

    public static GlobalListenerConfiguration get() {
        return GlobalConfiguration.all().get(GlobalListenerConfiguration.class);
    }

    private String url;

    public GlobalListenerConfiguration() {
        load();
    }

    public String getUrl() {
        return url;
    }

    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
        save();
    }

    public FormValidation doCheckUrl(@QueryParameter String value) {
        if (StringUtils.isEmpty(value)) {
            return FormValidation.warning("Please specify a url.");
        }
        return FormValidation.ok();
    }

}
