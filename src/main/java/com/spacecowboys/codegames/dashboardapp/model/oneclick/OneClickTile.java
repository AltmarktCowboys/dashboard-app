package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;

/**
 * Created by EDraser on 26.04.17.
 */
public class OneClickTile extends Tile {

    private String uri;
    private String username;
    private String password;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
