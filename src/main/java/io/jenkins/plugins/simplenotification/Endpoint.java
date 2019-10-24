package io.jenkins.plugins.simplenotification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;

public class Endpoint extends AbstractDescribableImpl<Endpoint> {

    private String url;
    private String include;
    private String exclude;
    private String itemType;
    private Boolean enableRegex;

    @DataBoundConstructor
    public Endpoint(String url) {
        this.url = url;
    }

    @DataBoundSetter
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @DataBoundSetter
    public void setInclude(String include) {
        this.include = include;
    }

    public String getInclude() {
        return include;
    }

    @DataBoundSetter
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getExclude() {
        return exclude;
    }

    @DataBoundSetter
    public void setEnableRegex(Boolean enableRegex) {
        this.enableRegex = enableRegex;
    }

    public Boolean isEnableRegex() {
        return enableRegex;
    }

    @DataBoundSetter
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public static List<String> getItemTypes() {
        List<String> itemTypes =  new ArrayList();
        itemTypes.add("Job");
        itemTypes.add("Agent");
        return itemTypes;
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<Endpoint> {
        @Override
        public String getDisplayName() {
            return "";
        }
        public ListBoxModel doFillItemTypeItems() {
            ListBoxModel itemTypes = new ListBoxModel();
            for (String itemType : getItemTypes()) {
                itemTypes.add(itemType);
            }
            return itemTypes;
        }
    }
}
