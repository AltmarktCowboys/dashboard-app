package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.configuration.Configuration;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OneClickCredentials {

    private static final Logger LOGGER = Logger.getLogger(OneClickCredentials.class);

    private final String accessNumber;
    private final String loginName;
    private final String password;
    private final String oneClickUrl;
    private OAuthToken portalToken = null;
    private String serviceUrl = null;

    public OneClickCredentials(String accessNumber, String loginName, String password, String oneClickUrl) {
        this.accessNumber = accessNumber;
        this.loginName = loginName;
        this.password = password;
        this.oneClickUrl = oneClickUrl;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getOneClickUrl() {
        return oneClickUrl;
    }

    public OAuthToken getPortalToken() {
        return portalToken;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public OAuthToken auth() {
        try {
            String bodyString;

            String subdomain = new URL(this.oneClickUrl).getHost();
            subdomain = subdomain.substring(0, subdomain.indexOf('.'));

            if(Strings.isNullOrEmpty(accessNumber)) {
                bodyString = String.format("grant_type=password&username=%1$s&password=%2$s&scope=oneclick offline_access",
                        URLEncoder.encode(subdomain + "\\" + loginName, "UTF-8"), URLEncoder.encode(password, "UTF-8"));
            } else {
                bodyString = String.format("grant_type=password&username=%1$s&password=%2$s&scope=oneclick offline_access",
                        URLEncoder.encode(subdomain + "\\" + accessNumber + "\\" + loginName, "UTF-8"), URLEncoder.encode(password, "UTF-8"));
            }
            OAuthToken token = executeTokenRequest(bodyString);
            portalToken = token;
            return token;
        } catch(Throwable throwable) {
            LOGGER.info(throwable.getMessage(), throwable);
        }

        return null;
    }

    private OAuthToken executeTokenRequest(String bodyString) throws Throwable {

        int timeout = Integer.parseInt(Configuration.getInstance().getOneClickTimeout());

        String tldomain = new URL(oneClickUrl).getHost();
        tldomain = tldomain.substring(tldomain.indexOf('.'));

        serviceUrl = oneClickUrl.substring(0, oneClickUrl.indexOf("://"))
                + "://services" + tldomain + "/ServiceHosts";

        URL url = new URL(String.format("%1$s/authority/connect/token", serviceUrl));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Host", new URL(oneClickUrl).getHost());
        connection.setRequestProperty("Authorization", "Basic cG9ydGFsOmFpcF9zZWNyZXQ=");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

        try (OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8")) {
            streamWriter.write(bodyString);
            streamWriter.flush();
        }

        byte[] bytes = IOUtils.toByteArray(connection.getInputStream());
        OAuthToken token =  JSON.read(bytes, OAuthToken.class);
        return token;
    }
}
