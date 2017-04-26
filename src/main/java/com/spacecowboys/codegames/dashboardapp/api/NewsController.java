package com.spacecowboys.codegames.dashboardapp.api;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.news.NewsContent;
import com.spacecowboys.codegames.dashboardapp.model.news.NewsService;
import com.spacecowboys.codegames.dashboardapp.model.news.NewsTile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by EDraser on 27.04.17.
 */
@Path("/tile/news")
public class NewsController {

    @GET
    @Path("/{userId}/{tileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTile(@PathParam("userId") String userId,
                            @PathParam("tileId") String tileId) {

        TileService<NewsTile> tileService = new TileService<>(userId, NewsTile.class);
        NewsTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            return Response.ok().entity(tile).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{userId}/{tileId}")
    public Response removeTile(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<NewsTile> tileService = new TileService<>(userId, NewsTile.class);
        tileService.removeTile(tileId);

        return Response.ok().build();
    }

    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTile(@PathParam("userId") String userId,
                            NewsTile tile) {

        TileService<NewsTile> tileService = new TileService<>(userId, NewsTile.class);
        if (Strings.isNullOrEmpty(tile.getId())) {
            tile.setId(UUID.randomUUID().toString());
        }
        tile.setTemplateId("news");
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTile(@PathParam("userId") String userId, NewsTile tile) {

        TileService<NewsTile> tileService = new TileService<>(userId, NewsTile.class);
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

        TileService<NewsTile> tileService = new TileService<>(userId, NewsTile.class);
        NewsTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            NewsService newsService = new NewsService(userId, tile);
            NewsContent content = newsService.getContent();
            return Response.ok(content).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
