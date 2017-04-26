package com.spacecowboys.codegames.dashboardapp.api;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherContent;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherService;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherTile;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
@Path("/tile/weather")
public class WeatherController {

    @GET
    @Path("/{userId}/{tileId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "retrieves dashboard layout for given user", notes = "my notes", response = String.class)
    public Response getTile(@PathParam("userId") String userId,
                            @PathParam("tileId") String tileId) {

        TileService<WeatherTile> tileService = new TileService<>(userId, WeatherTile.class);
        WeatherTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            return Response.ok().entity(tile).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{userId}/{tileId}")
    public Response removeTile(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<WeatherTile> tileService = new TileService<>(userId, WeatherTile.class);
        tileService.removeTile(tileId);

        return Response.ok().build();
    }

    private void setDefaultValues(WeatherTile tile) {
        if (Strings.isNullOrEmpty(tile.getId())) {
            tile.setId(UUID.randomUUID().toString());
        }
        tile.setTemplateId("weather");
    }

    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTile(@PathParam("userId") String userId,
                            WeatherTile tile) {

        TileService<WeatherTile> tileService = new TileService<>(userId, WeatherTile.class);
        setDefaultValues(tile);
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTile(@PathParam("userId") String userId, WeatherTile tile) {

        TileService<WeatherTile> tileService = new TileService<>(userId, WeatherTile.class);
        tile.setTemplateId("weather");
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @GET
    @Path("/{userId}/{tileId}/content")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContent(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<WeatherTile> tileService = new TileService<>(userId, WeatherTile.class);
        WeatherTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {

            WeatherService weatherService = new WeatherService(userId, tile);
            WeatherContent content = weatherService.getContent();
            return Response.ok(content).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
