package com.spacecowboys.codegames.dashboardapp.configuration;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

/**
 * umattner@addison.de on 26.04.2017 10:13
 */
public class Configuration {

    private static final Logger LOGGER = Logger.getLogger(Configuration.class);

    private String googleApiKey;

    private String twitterAccessToken;
    private String twitterTokenSecret;
    private String twitterConsumerKey;
    private String twitterConsumerSecret;

    private String weatherClientId;
    private String weatherClientSecret;

    private String auth0ClientId;
    private String auth0ClientSecret;
    private String auth0Domain;

    private String redisHost;
    private String redisPort;

    private String oneClickTimeout;
    private String oneClickTestUrl;
    private String oneClickTestAccessNumber;
    private String oneClickTestLoginName;
    private String oneClickTestPassword;

    private static volatile Configuration INSTANCE;

    public static Configuration getInstance() {
        if (INSTANCE == null) {
            synchronized (Configuration.class) {
                if (INSTANCE == null) {
                    Configuration instance = createConfiguration();
                    INSTANCE = instance;
                }
            }
        }

        return INSTANCE;
    }

    private Configuration() {
    }

    private Configuration(Properties properties) {

        googleApiKey = properties.getProperty("googleApiKey");
        twitterAccessToken = properties.getProperty("twitterAccessToken");
        twitterTokenSecret = properties.getProperty("twitterTokenSecret");
        twitterConsumerKey = properties.getProperty("twitterConsumerKey");
        twitterConsumerSecret = properties.getProperty("twitterConsumerSecret");
        weatherClientId = properties.getProperty("weatherClientId");
        weatherClientSecret = properties.getProperty("weatherClientSecret");
        auth0ClientId = properties.getProperty("auth0ClientId");
        auth0ClientSecret = properties.getProperty("auth0ClientSecret");
        auth0Domain = properties.getProperty("auth0Domain");
        redisHost = properties.getProperty("redisHost");
        redisPort = properties.getProperty("redisPort");
        oneClickTimeout = properties.getProperty("oneClickTimeout");
        oneClickTestUrl = properties.getProperty("oneClickTestUrl");
        oneClickTestAccessNumber = properties.getProperty("oneClickTestAccessNumber");
        oneClickTestLoginName = properties.getProperty("oneClickTestLoginName");
        oneClickTestPassword = properties.getProperty("oneClickTestPassword");
    }

    private static Configuration createConfiguration() {
        try {
            Properties props = new Properties();
            Configuration configuration = new Configuration();

            final String embeddedDefaults = "/defaults.conf";
            try (InputStream resourceAsStream = configuration.getClass().getResourceAsStream(embeddedDefaults)) {
                if (resourceAsStream != null) {
                    props.load(resourceAsStream);
                    LOGGER.info("default properties loaded from embedded resource file '" + embeddedDefaults + "'");
                }
            }

            String path = System.getProperty("conf.path");
            if (path != null) {
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    props.load(new StringReader(FileUtils.readFileToString(file)));
                    configuration = new Configuration(props);
                    LOGGER.info("properties loaded from file '" + file.getPath() + "'");
                    return configuration;
                }
            }
        } catch (IOException e) {
            LOGGER.error("error reading configuration", e);
        }

        LOGGER.warn("No configuration file provided! E.g. use -Dconf.path=/home/iam/mydashboard.conf. See resource default.conf for valid configuration keys.");
        // System.exit(1);
        return new Configuration(new Properties());
    }


    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public void setGoogleApiKey(String googleApiKey) {
        this.googleApiKey = googleApiKey;
    }

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTwitterTokenSecret() {
        return twitterTokenSecret;
    }

    public void setTwitterTokenSecret(String twitterTokenSecret) {
        this.twitterTokenSecret = twitterTokenSecret;
    }

    public String getTwitterConsumerKey() {
        return twitterConsumerKey;
    }

    public void setTwitterConsumerKey(String twitterConsumerKey) {
        this.twitterConsumerKey = twitterConsumerKey;
    }

    public String getTwitterConsumerSecret() {
        return twitterConsumerSecret;
    }

    public void setTwitterConsumerSecret(String twitterConsumerSecret) {
        this.twitterConsumerSecret = twitterConsumerSecret;
    }

    public String getWeatherClientId() {
        return weatherClientId;
    }

    public void setWeatherClientId(String weatherClientId) {
        this.weatherClientId = weatherClientId;
    }

    public String getWeatherClientSecret() {
        return weatherClientSecret;
    }

    public void setWeatherClientSecret(String weatherClientSecret) {
        this.weatherClientSecret = weatherClientSecret;
    }

    public String getAuth0ClientId() {
        return auth0ClientId;
    }

    public void setAuth0ClientId(String auth0ClientId) {
        this.auth0ClientId = auth0ClientId;
    }

    public String getAuth0ClientSecret() {
        return auth0ClientSecret;
    }

    public void setAuth0ClientSecret(String auth0ClientSecret) {
        this.auth0ClientSecret = auth0ClientSecret;
    }

    public String getAuth0Domain() {
        return auth0Domain;
    }

    public void setAuth0Domain(String auth0Domain) {
        this.auth0Domain = auth0Domain;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getOneClickTimeout() {
        return oneClickTimeout;
    }

    public void setOneClickTimeout(String oneClickTimeout) {
        this.oneClickTimeout = oneClickTimeout;
    }

    public String getOneClickTestUrl() {
        return oneClickTestUrl;
    }

    public void setOneClickTestUrl(String oneClickTestUrl) {
        this.oneClickTestUrl = oneClickTestUrl;
    }

    public String getOneClickTestAccessNumber() {
        return oneClickTestAccessNumber;
    }

    public void setOneClickTestAccessNumber(String oneClickTestAccessNumber) {
        this.oneClickTestAccessNumber = oneClickTestAccessNumber;
    }

    public String getOneClickTestLoginName() {
        return oneClickTestLoginName;
    }

    public void setOneClickTestLoginName(String oneClickTestLoginName) {
        this.oneClickTestLoginName = oneClickTestLoginName;
    }

    public String getOneClickTestPassword() {
        return oneClickTestPassword;
    }

    public void setOneClickTestPassword(String oneClickTestPassword) {
        this.oneClickTestPassword = oneClickTestPassword;
    }
}
