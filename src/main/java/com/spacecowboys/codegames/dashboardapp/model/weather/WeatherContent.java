package com.spacecowboys.codegames.dashboardapp.model.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDraser on 26.04.17.
 */
public class WeatherContent {

    private String description;
    private String lastUpdate;
    private Condition currentCondition = new Condition();
    private List<ForecastItem> forecast = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Condition getCurrentCondition() {
        return currentCondition;
    }

    public List<ForecastItem> getForecast() {
        return forecast;
    }
}
