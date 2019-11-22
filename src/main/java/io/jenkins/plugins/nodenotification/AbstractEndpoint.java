package io.jenkins.plugins.nodenotification;

import java.util.ArrayList;
import java.util.List;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public abstract class AbstractEndpoint extends AbstractDescribableImpl<AbstractEndpoint> {

    @SuppressWarnings("unchecked")
    public List<Descriptor<AbstractEndpoint>> getEndpointDescriptors() {
        List<Descriptor<AbstractEndpoint>> descriptors = new ArrayList<Descriptor<AbstractEndpoint>>();
        Jenkins j = Jenkins.get();
        descriptors.add(j.getDescriptor(EmailEndpoint.class));
        descriptors.add(j.getDescriptor(SMSEndpoint.class));
        return descriptors;
    }

}
