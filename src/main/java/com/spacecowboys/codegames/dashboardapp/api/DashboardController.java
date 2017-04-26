package com.spacecowboys.codegames.dashboardapp.api;

import com.spacecowboys.codegames.dashboardapp.model.dashboard.DashboardLayout;
import com.spacecowboys.codegames.dashboardapp.model.dashboard.DashboardLayoutService;
import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by EDraser on 26.04.17.
 */
@Path("/dashboard")
public class DashboardController {

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "retrieves dashboard layout for given user", notes = "my notes", response = String.class)
    public Response getDashboardLayout(@PathParam("userId") String userId) {

        DashboardLayoutService dashboardLayoutService = new DashboardLayoutService(userId);
        DashboardLayout dashboardLayout = dashboardLayoutService.get();
        if (dashboardLayout != null) {
            TileService<Tile> tileService = new TileService<>(userId, Tile.class);
            List<Tile> tiles = tileService.retrieveTiles();

            DashboardContract dashboardContract = new DashboardContract();
            dashboardContract.setLayout(dashboardLayout.getContent());
            dashboardContract.getTiles().addAll(tiles);

            return Response.ok().entity(dashboardContract).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
