package com.spacecowboys.codegames.dashboardapp.model.news;

import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;

/**
 * Created by EDraser on 26.04.17.
 */
public class NewsTile extends Tile {

    private String category;
    private String newsUrl;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
