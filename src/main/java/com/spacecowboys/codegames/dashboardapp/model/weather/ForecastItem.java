package com.spacecowboys.codegames.dashboardapp.model.weather;

/**
 * Created by EDraser on 26.04.17.
 */
public class ForecastItem {

    private String text;
    private String date;
    private String high;
    private String low;
    private String day;

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

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
