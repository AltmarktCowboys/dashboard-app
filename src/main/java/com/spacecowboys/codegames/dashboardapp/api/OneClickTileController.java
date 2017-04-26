package com.spacecowboys.codegames.dashboardapp.api;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.oneclick.OneClickService;
import com.spacecowboys.codegames.dashboardapp.model.oneclick.OneClickSession;
import com.spacecowboys.codegames.dashboardapp.model.oneclick.OneClickTile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
@Path("/tile/oneclick")
public class OneClickTileController {

    @GET
    @Path("/{userId}/{tileId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "retrieves dashboard layout for given user", notes = "my notes", response = String.class)
    public Response getTile(@PathParam("userId") String userId,
                            @PathParam("tileId") String tileId) {

        TileService<OneClickTile> tileService = new TileService<>(userId, OneClickTile.class);
        OneClickTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            return Response.ok().entity(tile).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{userId}/{tileId}")
    public Response removeTile(@PathParam("userId") String userId,
                               @PathParam("tileId") String tileId) {

        TileService<OneClickTile> tileService = new TileService<>(userId, OneClickTile.class);
        tileService.removeTile(tileId);

        return Response.ok().build();
    }

    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTile(@PathParam("userId") String userId,
                            OneClickTile tile) {

        TileService<OneClickTile> tileService = new TileService<>(userId, OneClickTile.class);
        if (Strings.isNullOrEmpty(tile.getId())) {
            tile.setId(UUID.randomUUID().toString());
        }
        tileService.putTile(tile);

        return Response.ok(tile).build();
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTile(@PathParam("userId") String userId, OneClickTile tile) {

        TileService<OneClickTile> tileService = new TileService<>(userId, OneClickTile.class);
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

        TileService<OneClickTile> tileService = new TileService<>(userId, OneClickTile.class);
        OneClickTile tile = tileService.getTile(userId, tileId);
        if (tile != null) {
            OneClickService oneClickService = new OneClickService();
            OneClickSession oneClickSession = oneClickService.getOneClickSession(tile);

            return Response.ok(oneClickSession).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
