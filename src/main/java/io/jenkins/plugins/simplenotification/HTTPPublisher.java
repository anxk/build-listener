package io.jenkins.plugins.simplenotification;

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

public class HTTPPublisher {

    private static Logger LOGGER = Logger.getLogger(HTTPPublisher.class.getName());
    private int socketTimeout = Utils.socketTimeout;
    private int connectTimeout = Utils.connectTimeout;
    private int requestTimeout = Utils.requestTimeout;

    public void publish(String url, Event event) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(requestTimeout)
            .build();
        HttpPost req = new HttpPost(url);
        req.setConfig(requestConfig);
        try {
            StringEntity requestEntity = new StringEntity(event.toJsonString(), "UTF-8");
            requestEntity.setContentEncoding("UTF-8");
            req.setHeader("Content-type", "application/json");
            req.setEntity(requestEntity);
            try {
                CloseableHttpResponse rsp = httpclient.execute(req);
                StatusLine status = rsp.getStatusLine();
                LOGGER.log(Level.INFO, "Status line: ", status);
            } catch (ClientProtocolException e) {
                LOGGER.log(Level.WARNING, "Protocol error", e);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "A problem occurred or The connection was aborted", e);
            }
        } catch (UnsupportedCharsetException e) {
            LOGGER.log(Level.WARNING, "Unsupported charset", e);
        }
    }
}
