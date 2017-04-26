package com.spacecowboys.codegames.dashboardapp;

/**
 * Created by EDraser on 25.04.17.
 */

import com.spacecowboys.codegames.dashboardapp.api.DashboardController;
import com.spacecowboys.codegames.dashboardapp.api.HelloWorldController;
import com.spacecowboys.codegames.dashboardapp.api.OneClickTileController;

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
        return classes;
    }
}
