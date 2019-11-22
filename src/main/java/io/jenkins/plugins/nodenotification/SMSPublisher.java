package io.jenkins.plugins.nodenotification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import hudson.model.Computer;
import hudson.slaves.OfflineCause;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

public class SMSPublisher {

    private static Logger LOGGER = Logger.getLogger(SMSPublisher.class.getName());
    private static String DEFAULT_PLATLOAD_RECIPIENT_KEY = "RECIPIENT";
    private static String DEFAULT_PLAYLOAD_MESSAGE_KEY = "MESSAGE";

    public void publish(OfflineCause cause, Computer c, List<PropertyEntry> entrys) {
        for (PropertyEntry entry : entrys) {
            if (entry.getType().equals("sms")) {
                for (AbstractEndpoint endpoint: NodeNotificationConfiguration.get().getEndpoints()) {
                    if (endpoint.getDescriptor().getDisplayName().equals("SMS")) {
                        List<String> playloads = createPlayloads(((SMSEndpoint) endpoint).getPlayloadTemplate(), cause, entry, c);
                        for (String playload : playloads) {
                            _publish(((SMSEndpoint) endpoint).getUrl(), playload);
                        }
                    }
                }
            }
        }
    }

    public String createMessage(OfflineCause cause, Computer c, PropertyEntry entry) {
        Jenkins j = Jenkins.get();
        String jenkinsRootUrl = "";
        if (j != null) {
            try {
                String rootUrl = j.getRootUrl();
                if (rootUrl != null) {
                    jenkinsRootUrl = rootUrl;
                }
            } catch (IllegalStateException e) {
                LOGGER.log(Level.WARNING, "Can not obtain jenkins root url", e);
            }
        }
        StringBuilder message = new StringBuilder();
        message.append("\nNode Name: " + c.getName());
        message.append("\nNode Labels: " + c.getAssignedLabels());
        message.append("\nNode URL: " + jenkinsRootUrl + c.getUrl());
        String additionalMessage = entry.getMessage();
        if (!additionalMessage.trim().equals("")) {
            message.append("\nAdditional message: " + entry.getMessage());
        }
        return message.toString();
    }

    public List<String> createRecipients(String recipients, Computer c) {
        List<String> to = new ArrayList<String>();
        Iterator<String> iter = Splitter.on(Pattern.compile("[,; ]"))
            .omitEmptyStrings()
            .trimResults()
            .split(recipients)
            .iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            try {
                to.add(str);
            } catch (UnsupportedOperationException
                    | ClassCastException
                    | NullPointerException
                    | IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Failed to add recipient " + str + " for " + c.getName(), e);
            }
        }
        return to;
    }

    @SuppressWarnings("unchecked")
    public List<String> createPlayloads(String playloadTemplate, OfflineCause cause, PropertyEntry entry, Computer c) {
        JSONObject playloadTemplateJson = JSONObject.fromObject(playloadTemplate);
        List<String> playloads = new ArrayList<>();
        for (String recipient : createRecipients(entry.getRecipients(), c)) {
            JSONObject playload = playloadTemplateJson;
            Iterator<Map.Entry<String, String>> it = playload.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> kv = it.next();
                if (kv.getValue().equals(DEFAULT_PLATLOAD_RECIPIENT_KEY)) {
                    playload.put(kv.getKey(), recipient);
                } else if (kv.getValue().equals(DEFAULT_PLAYLOAD_MESSAGE_KEY)) {
                    playload.put(kv.getKey(), createMessage(cause, c, entry));
                }
            }
            playloads.add(playload.toString());
        }
        return playloads;
    }

    public void _publish(String url, String playload) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
        try {
            HttpPost request = new HttpPost(url);
            request.setConfig(requestConfig);
            request.setHeader("Content-type", "application/json");
            StringEntity requestEntity = new StringEntity(playload, "UTF-8");
            request.setEntity(requestEntity);
            CloseableHttpResponse response = httpclient.execute(request);
            LOGGER.log(Level.INFO, "Send SMS by " + url + " " + response.getStatusLine());
        } catch (IllegalArgumentException | IOException e) {
            LOGGER.log(Level.WARNING, "Failed to send SMS", e);
        }
    }

}
