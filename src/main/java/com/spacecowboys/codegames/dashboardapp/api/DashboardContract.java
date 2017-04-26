package com.spacecowboys.codegames.dashboardapp.api;

import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDraser on 26.04.17.
 */
public class DashboardContract {
    private String userId;
    private String layout;
    private List<Tile> tiles = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
