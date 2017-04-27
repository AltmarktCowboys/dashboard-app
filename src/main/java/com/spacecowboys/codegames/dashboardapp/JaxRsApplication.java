package com.spacecowboys.codegames.dashboardapp;

/**
 * Created by EDraser on 25.04.17.
 */

import com.spacecowboys.codegames.dashboardapp.api.*;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@ApplicationPath("/rest")
public class JaxRsApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HelloWorldController.class);
        classes.add(OneClickTileController.class);
        classes.add(DashboardController.class);
        classes.add(WeatherController.class);
        classes.add(CORSFilter.class);
        classes.add(TwitterController.class);
        classes.add(NewsController.class);
        classes.add(JiraController.class);
        return classes;
    }
}
