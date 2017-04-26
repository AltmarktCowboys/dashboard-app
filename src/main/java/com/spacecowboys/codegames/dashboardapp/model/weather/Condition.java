package com.spacecowboys.codegames.dashboardapp.model.weather;

/**
 * Created by EDraser on 26.04.17.
 */
public class Condition {

    private String text;
    private String date;
    private String temp;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
