package io.jenkins.plugins.simplenodenotification;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.StatusLine;

public class Publisher {

    private static Logger LOGGER = Logger.getLogger(Publisher.class.getName());

    public static void publish(Endpoint endpoint, Event event) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
        HttpPost req = new HttpPost(endpoint.getUrl());
        req.setConfig(requestConfig);
        req.setHeader("Content-type", "application/json");

        try {
            StringEntity requestEntity = new StringEntity(event.toJson().toString(), "UTF-8");
            req.setEntity(requestEntity);
        } catch (UnsupportedCharsetException e) {
            LOGGER.log(Level.WARNING, "Unsupported charset", e);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "", e);
        }
        
        try {
            CloseableHttpResponse rsp = httpclient.execute(req);
            StatusLine status = rsp.getStatusLine();
            if (status != null) {
                LOGGER.log(Level.INFO, "Notification to " + endpoint.getUrl() + " " + status);
            }
        } catch (ClientProtocolException e) {
                LOGGER.log(Level.WARNING, "Protocol error", e);
        } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Connection was aborted", e);
        }
    }
}
