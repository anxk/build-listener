package io.jenkins.plugins.nodenotification;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.Descriptor;

public class EmailEndpoint extends AbstractEndpoint {

    @DataBoundConstructor
    public EmailEndpoint() {}

    @Extension
    public static final class DescriptorImpl extends Descriptor<AbstractEndpoint> {

        @Override
        public String getDisplayName() {
            return "Email";
        }

    }

}
