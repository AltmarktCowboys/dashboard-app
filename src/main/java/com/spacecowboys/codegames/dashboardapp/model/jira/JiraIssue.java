package com.spacecowboys.codegames.dashboardapp.model.jira;

/**
 * Created by EDraser on 27.04.17.
 */
public class JiraIssue {

    private String name;
    private String title;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
