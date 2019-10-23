package io.jenkins.plugins.globallistener;

import java.util.Map;

public interface State {
    public String getId();
    public Map<String, String> getDescription();
}
