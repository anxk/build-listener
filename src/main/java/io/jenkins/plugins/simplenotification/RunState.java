package io.jenkins.plugins.simplenotification;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import hudson.model.Cause;
import hudson.model.Run;
import hudson.model.Result;
import jenkins.model.Jenkins;

public class RunState implements ItemState {

    private static Logger LOGGER = Logger.getLogger(HTTPPublisher.class.getName());
    private String id;
    private String name;
    private Map<String, String> description;

    RunState(Run<?, ?> run) {
        setId();
        setName(run);
        setDescription(run);
    }

    public String getId() {
        return id;
    }

    private void setId() {
        this.id =  UUID.randomUUID().toString();
    }

    public Map<String, String> getDescription() {
        return description;
    }

    private void setDescription(Run<?, ?> run) {
        Map<String, String> description = new HashMap<>();
        description.put("name", getFullName(run));
        description.put("uri", getUri(run));
        description.put("cause", getCauses(run));
        description.put("result", getResult(run));
        description.put("duration", getDuration(run));
        this.description = description;
    }

    private void setName(Run<?, ?> run) {
        this.name = getFullName(run);
    }

    public String getName() {
        return name;
    }

    private String getFullName(Run<?, ?> run) {
        return run.getFullDisplayName();
    }

    private String getDuration(Run<?, ?> run) {
        return Long.toString(run.getDuration());
    }

    private String getUri(Run<?, ?> run) {
        String uri = "";
        try {
            String rootUrl = Jenkins.get().getRootUrl();
            if (rootUrl != null) {
                uri = rootUrl + run.getUrl();
            } else {
                LOGGER.log(Level.WARNING , "Jenkins root url was not configured");
                uri = run.getUrl();
            }
        } catch (IllegalStateException e) {
            LOGGER.log(Level.WARNING , "", e);
        }
        return uri;
    }

    private String getCauses(Run<?, ?> run) {
        StringBuilder causesBuilder = new StringBuilder();
        List<Cause> causes = run.getCauses();
        for (int i = 0; i < causes.size(); i++) {
            if (i != 0) {
                causesBuilder.append(", ");
            }
            causesBuilder.append(causes.get(i).getShortDescription());
        }
        return causesBuilder.toString();
    }

    private String getResult(Run<?, ?> run) {
        Result result = run.getResult();
        String resultString = "";
        if (result != null) {
            resultString = result.toString();
        }
        return resultString;
    }
}
