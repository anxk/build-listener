package io.jenkins.plugins.nodenotification;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.StaplerRequest;

@Extension
public class NodeNotificationConfiguration extends GlobalConfiguration {

    private List<AbstractEndpoint> endpoints;

    public static NodeNotificationConfiguration get() {
        return GlobalConfiguration.all().get(NodeNotificationConfiguration.class);
    }

    public NodeNotificationConfiguration() {
        this.endpoints = new ArrayList<AbstractEndpoint>();
        load();
    }

    @Override
    public boolean configure(final StaplerRequest req, final JSONObject formData) {
        setEndpoints(req.bindJSONToList(AbstractEndpoint.class, formData.get("endpoints")));
        return false;
    }

    public List<AbstractEndpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<AbstractEndpoint> endpoints) {
        this.endpoints = endpoints;
        save();
    }
    
}
