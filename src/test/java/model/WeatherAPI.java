package model;

import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherContent;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherService;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherTile;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
public class WeatherAPI {

    @Test
    public void testQuery() {

        WeatherTile weatherTile = new WeatherTile();
        weatherTile.setCity("Böblingen");
        weatherTile.setId(UUID.randomUUID().toString());
        weatherTile.setTemplateId("weather");
        weatherTile.setUserId(UUID.randomUUID().toString());
        weatherTile.setTitle("ttitle");

        WeatherService weatherService = new WeatherService(weatherTile.getUserId(), weatherTile);
        WeatherContent content = weatherService.getContent();
    }
}
