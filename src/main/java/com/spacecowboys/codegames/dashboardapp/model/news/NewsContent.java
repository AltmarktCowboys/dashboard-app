package com.spacecowboys.codegames.dashboardapp.model.news;

import com.spacecowboys.codegames.dashboardapp.tools.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDraser on 26.04.17.
 */
public class NewsContent {

    private String title;
    private String link;
    private String imageUrl;
    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    private LocalDateTime pubDate;
    private List<FeedItem> feeds = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public List<FeedItem> getFeeds() {
        return feeds;
    }
}
