package com.spacecowboys.codegames.dashboardapp.api;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.jira.JiraContent;
import com.spacecowboys.codegames.dashboardapp.model.jira.JiraService;
import com.spacecowboys.codegames.dashboardapp.model.jira.JiraTile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by EDraser on 27.04.17.
 */
@Path("/tile/jira")
public class JiraController {

    @GET
    @Path("/{userId}/{tileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTile(@PathParam("userId") String userId,
                            @PathParam("tileId") String tileId) {

        TileService<JiraTile> tileService = new TileService<>(userId, JiraTile.class);
        JiraTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            return Response.ok().entity(tile).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{userId}/{tileId}")
    public Response removeTile(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<JiraTile> tileService = new TileService<>(userId, JiraTile.class);
        tileService.removeTile(tileId);

        return Response.ok().build();
    }

    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTile(@PathParam("userId") String userId,
                            JiraTile tile) {

        TileService<JiraTile> tileService = new TileService<>(userId, JiraTile.class);
        if (Strings.isNullOrEmpty(tile.getId())) {
            tile.setId(UUID.randomUUID().toString());
        }
        tile.setTemplateId("jira");
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTile(@PathParam("userId") String userId, JiraTile tile) {

        TileService<JiraTile> tileService = new TileService<>(userId, JiraTile.class);
        tile.setTemplateId("jira");
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @GET
    @Path("/{userId}/{tileId}/content")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContent(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<JiraTile> tileService = new TileService<>(userId, JiraTile.class);
        JiraTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            JiraService jiraService = new JiraService(tile, userId);
            JiraContent content = jiraService.getContent();
            return Response.ok(content).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
