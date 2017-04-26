package com.spacecowboys.codegames.dashboardapp.model.weather;

import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;

/**
 * Created by EDraser on 26.04.17.
 */
public class WeatherTile extends Tile {

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
