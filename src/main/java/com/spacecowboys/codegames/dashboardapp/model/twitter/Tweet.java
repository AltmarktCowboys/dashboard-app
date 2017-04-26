package com.spacecowboys.codegames.dashboardapp.model.twitter;

import com.spacecowboys.codegames.dashboardapp.tools.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by EDraser on 26.04.17.
 */
public class Tweet {

    private String text;
    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    private LocalDateTime createdAt;
    private TwitterUser user = new TwitterUser();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TwitterUser getUser() {
        return user;
    }
}
