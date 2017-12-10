package com.spacecowboys.codegames.dashboardapp;

import com.spacecowboys.codegames.dashboardapp.api.CORSFilter;
import com.spacecowboys.codegames.dashboardapp.api.DashboardController;
import com.spacecowboys.codegames.dashboardapp.api.HelloWorldController;
import com.spacecowboys.codegames.dashboardapp.api.OneClickTileController;
import com.spacecowboys.codegames.dashboardapp.model.oneclick.OneClickTile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.JAXRS;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.spi.api.JARArchive;
import org.wildfly.swarm.swagger.SwaggerArchive;
import org.wildfly.swarm.undertow.WARArchive;


/**
 * Created by EDraser on 25.04.17.
 *
 * Hosts Swarm on localhost:8080 per default
 */
public class SwarmMain {
    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm(); // Always do this at first, since this also configures the logging stuff etc.

        com.spacecowboys.codegames.dashboardapp.configuration.Configuration.getInstance();
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        registerResources(deployment);
        configureSwagger(deployment);
        deployment.addAllDependencies();

        Level logLevel = Level.INFO;
        swarm.fraction(new LoggingFraction()
                .formatter("PATTERN", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n")
                .consoleHandler(logLevel, "PATTERN")
                .rootLogger(logLevel, "CONSOLE"));
        swarm.start().deploy(deployment);
    }

    private static void configureSwagger(JAXRSArchive deployment) {
        SwaggerArchive archive = deployment.as(SwaggerArchive.class);
        // Tell swagger where our resources are
        archive.setResourcePackages("com.spacecowboys.codegames.dashboardapp.api");
        archive.setTitle("My Awesome Dashboard API");
        archive.setHost("localhost:8080");
    }

    /***
     * register all resources (endpoints) here
     * @param deployment
     */
    private static void registerResources(JAXRSArchive deployment) throws Exception {
        deployment.addPackages(true, "com.spacecowboys.codegames.dashboardapp");
    }
}
