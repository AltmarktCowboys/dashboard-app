package com.spacecowboys.codegames.dashboardapp.model.jira;

import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;

/**
 * Created by EDraser on 27.04.17.
 */
public class JiraTile extends Tile {

    private String username;
    private String password;
    private String jiraUrl;

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

    public String getJiraUrl() {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }
}
