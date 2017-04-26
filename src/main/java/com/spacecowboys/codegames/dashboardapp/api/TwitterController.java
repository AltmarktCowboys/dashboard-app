package com.spacecowboys.codegames.dashboardapp.api;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.model.twitter.TwitterContent;
import com.spacecowboys.codegames.dashboardapp.model.twitter.TwitterService;
import com.spacecowboys.codegames.dashboardapp.model.twitter.TwitterTile;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherContent;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
@Path("/tile/twitter")
public class TwitterController {

    @GET
    @Path("/{userId}/{tileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTile(@PathParam("userId") String userId,
                            @PathParam("tileId") String tileId) {

        TileService<TwitterTile> tileService = new TileService<>(userId, TwitterTile.class);
        TwitterTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            return Response.ok().entity(tile).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{userId}/{tileId}")
    public Response removeTile(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<TwitterTile> tileService = new TileService<>(userId, TwitterTile.class);
        tileService.removeTile(tileId);

        return Response.ok().build();
    }

    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTile(@PathParam("userId") String userId,
                            TwitterTile tile) {

        TileService<TwitterTile> tileService = new TileService<>(userId, TwitterTile.class);
        if (Strings.isNullOrEmpty(tile.getId())) {
            tile.setId(UUID.randomUUID().toString());
            tile.setTemplateId("twitter");
        }
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTile(@PathParam("userId") String userId, TwitterTile tile) {

        TileService<TwitterTile> tileService = new TileService<>(userId, TwitterTile.class);
        if (Strings.isNullOrEmpty(tile.getId())) {
            tile.setId(UUID.randomUUID().toString());
        }
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @GET
    @Path("/{userId}/{tileId}/content")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContent(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<TwitterTile> tileService = new TileService<>(userId, TwitterTile.class);
        TwitterTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            TwitterService twitterService = new TwitterService(userId, tile);
            TwitterContent content = twitterService.getContent();
            return Response.ok(content).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
