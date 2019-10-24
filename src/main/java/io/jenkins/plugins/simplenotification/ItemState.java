package io.jenkins.plugins.simplenotification;

import java.util.Map;

public interface ItemState {
    public String getId();
    public String getName();
    public Map<String, String> getDescription();
}
