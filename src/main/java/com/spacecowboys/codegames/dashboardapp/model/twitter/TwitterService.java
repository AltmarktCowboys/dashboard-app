package com.spacecowboys.codegames.dashboardapp.model.twitter;

import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spacecowboys.codegames.dashboardapp.configuration.Configuration;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.model.weather.ForecastItem;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherContent;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherTile;
import com.spacecowboys.codegames.dashboardapp.tools.DateTimeTools;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import com.spacecowboys.codegames.dashboardapp.tools.RestClient;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EDraser on 26.04.17.
 */
public class TwitterService {

    private static Logger LOGGER = Logger.getLogger(TwitterService.class);
    private String userId;
    private TwitterTile tile;

    public TwitterService(String userId, TwitterTile tile) {
        this.userId = userId;
        this.tile = tile;
    }


    public TwitterContent getContent() {

        TwitterContent content = null;
        TileService<TwitterTile> tileService = new TileService<>(tile.getUserId(), TwitterTile.class);
        String cachedContent = tileService.getTileContent(tile.getId());
        if (!Strings.isNullOrEmpty(cachedContent)) {
            content = JSON.fromString(cachedContent, TwitterContent.class);
        } else {
            content = new TwitterContent();
            Twitter twitter = createTwitterInstance();

            try {
                ResponseList<Status> homeTimeline = twitter.getHomeTimeline();

                for (Status status :
                        homeTimeline) {
                    Tweet tweet = new Tweet();
                    tweet.setText(status.getText());
                    tweet.setCreatedAt(DateTimeTools.toLocalDateTime(status.getCreatedAt()));
                    tweet.getUser().setName(status.getUser().getName());
                    tweet.getUser().setScreenName(status.getUser().getScreenName());
                    tweet.getUser().setProfileImageUrl(status.getUser().getProfileImageURL());
                    tweet.getUser().setProfileUrl(status.getUser().getURL());
                    content.getTweets().add(tweet);
                }
            } catch (TwitterException e) {
               LOGGER.error("failed to load tweets", e);
            }
            tileService.setTileContent(tile.getId(), JSON.toString(content, TwitterContent.class));
        }

        return content;
    }

    private Twitter createTwitterInstance() {

        Configuration configuration = Configuration.getInstance();

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthAccessToken(configuration.getTwitterAccessToken())
                .setOAuthAccessTokenSecret(configuration.getTwitterTokenSecret())
                .setOAuthConsumerKey(configuration.getTwitterConsumerKey())
                .setOAuthConsumerSecret(configuration.getTwitterConsumerSecret());

        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();

        return twitter;
    }
}
