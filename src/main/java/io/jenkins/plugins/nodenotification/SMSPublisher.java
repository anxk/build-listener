package io.jenkins.plugins.nodenotification;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import io.jenkins.plugins.nodenotification.NotificationNodeProperty.Entry;

public class SMSPublisher {

    private static Logger LOGGER = Logger.getLogger(SMSPublisher.class.getName());

    public static void publish(String cause, String nodeInfo, List<NotificationNodeProperty.Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getType() == "sms") {
                String message = build(cause, nodeInfo, entry);
                for (Endpoint endpoint: NodeNotificationConfiguration.get().getEndpoints()) {
                    if (endpoint.getType() == "sms") {
                        _publish(endpoint.getUrl(), message);
                    }
                }
            }
        }
    }

    public static String build(String cause, String nodeInfo, NotificationNodeProperty.Entry entry) {
        StringBuilder message = new StringBuilder();
        message.append("Offline Cause: " + cause);
        message.append("\nNode Info: " + nodeInfo);
        message.append("\nMessage: " + entry.getMessage());
        return message.toString();
    }
    
    public static void _publish(String url, String message) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();

        try {
            HttpPost request = new HttpPost(url);
            request.setConfig(requestConfig);
            request.setHeader("Content-type", "text/plain");
            StringEntity requestEntity = new StringEntity(message, "UTF-8");
            request.setEntity(requestEntity);
            CloseableHttpResponse response = httpclient.execute(request);
            LOGGER.log(Level.INFO, "Send SMS by " + url + " " + response.getStatusLine());
        } catch (UnsupportedCharsetException e) {
            LOGGER.log(Level.WARNING, "Unsupported charset", e);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid argument", e);
        } catch (ClientProtocolException e) {
            LOGGER.log(Level.WARNING, "Protocol error", e);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Connection was aborted", e);
        }
    }

}
