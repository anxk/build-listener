package io.jenkins.plugins.globallistener;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;


@Extension
public class GlobalListenerConfiguration extends GlobalConfiguration {

    public static GlobalListenerConfiguration get() {
        return GlobalConfiguration.all().get(GlobalListenerConfiguration.class);
    }

    private List<Channel> channels;

    public GlobalListenerConfiguration() {
        load();
    }

    public List<Channel> getChannels() {
        return channels;
    }

    @DataBoundSetter
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
        save();
    }

    // /**
    //  * FIX: balabala
    //  * @param value
    //  * @return
    //  */
    // public FormValidation doCheckUrl(@QueryParameter String value) {
    //     if (StringUtils.isEmpty(value)) {
    //         return FormValidation.warning("Please specify a url.");
    //     }
    //     return FormValidation.ok();
    // }

}
