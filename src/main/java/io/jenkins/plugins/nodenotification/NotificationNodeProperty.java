package io.jenkins.plugins.nodenotification;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import jenkins.model.Jenkins;

@Extension
public class NotificationNodeProperty extends NodeProperty<Node> {

    private List<Entry> entrys;

    @DataBoundConstructor
    public NotificationNodeProperty() {
        this.entrys = new ArrayList<Entry>();
    }

    @DataBoundSetter
    public void setEntrys(List<Entry> entrys) {
        this.entrys = entrys;
    }

    public List<Entry> getEntrys() {
        return entrys;
    }

    @Extension
    public static class DescriptorImpl extends NodePropertyDescriptor {

        @Override
		public String getDisplayName() {
			return "Node Notification";
		}

        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return nodeType != Jenkins.class;
        }

    }

}