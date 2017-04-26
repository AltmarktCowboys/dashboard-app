package com.spacecowboys.codegames.dashboardapp.model.weather;

import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import com.spacecowboys.codegames.dashboardapp.tools.RestClient;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by EDraser on 26.04.17.
 */
public class WeatherService {

    private String userId;
    private WeatherTile weatherTile;

    public WeatherService(String userId, WeatherTile weatherTile) {
        this.userId = userId;
        this.weatherTile = weatherTile;
    }

    public WeatherContent getContent() {

        WeatherContent content = null;
        TileService<WeatherTile> tileService = new TileService<>(weatherTile.getUserId(), WeatherTile.class);
        String cachedContent = tileService.getTileContent(weatherTile.getId());
        if (!Strings.isNullOrEmpty(cachedContent)) {
            content = JSON.fromString(cachedContent, WeatherContent.class);
        } else {

            String queryString = String.format("select * from weather.forecast where woeid in " +
                    "(select woeid from geo.places(1) where text=\"%s\")", weatherTile.getCity());
            RestClient restClient = new RestClient(null, "https://query.yahooapis.com/v1/public/yql");
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("q", queryString);
            String result = restClient.getResult(queryParams, String.class);
            JsonObject body = JSON.fromString(result, JsonObject.class);
            JsonObject cityResult = body.getAsJsonObject("query").getAsJsonObject("results").getAsJsonObject("channel").getAsJsonObject("item");

            content = new WeatherContent();
            content.setDescription(cityResult.get("title").getAsString());
            JsonObject condition = cityResult.getAsJsonObject("condition");
            content.getCurrentCondition().setDate(condition.get("date").getAsString());
            content.getCurrentCondition().setTemp(condition.get("temp").getAsString());
            content.getCurrentCondition().setText(condition.get("text").getAsString());

            JsonArray forecast = cityResult.getAsJsonArray("forecast");
            for (JsonElement jsonValue :
                    forecast) {
                if (jsonValue.isJsonObject()) {
                    JsonObject jsonObject = jsonValue.getAsJsonObject();
                    ForecastItem forecastItem = new ForecastItem();
                    forecastItem.setDate(jsonObject.get("date").getAsString());
                    forecastItem.setDay(jsonObject.get("day").getAsString());
                    forecastItem.setHigh(jsonObject.get("high").getAsString());
                    forecastItem.setLow(jsonObject.get("low").getAsString());
                    forecastItem.setText(jsonObject.get("text").getAsString());
                    content.getForecast().add(forecastItem);
                }
            }
            // grep result
            tileService.setTileContent(weatherTile.getId(), JSON.toString(content, WeatherContent.class));
        }

        return content;
    }
}
