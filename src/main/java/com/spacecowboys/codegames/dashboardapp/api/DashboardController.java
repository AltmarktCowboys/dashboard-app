package com.spacecowboys.codegames.dashboardapp.api;

import com.spacecowboys.codegames.dashboardapp.model.dashboard.DashboardLayout;
import com.spacecowboys.codegames.dashboardapp.model.dashboard.DashboardLayoutService;
import com.spacecowboys.codegames.dashboardapp.model.jira.JiraTile;
import com.spacecowboys.codegames.dashboardapp.model.news.NewsTile;
import com.spacecowboys.codegames.dashboardapp.model.oneclick.OneClickTile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.Tile;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.model.twitter.TwitterTile;
import com.spacecowboys.codegames.dashboardapp.model.weather.WeatherTile;
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

        DashboardContract dashboardContract = new DashboardContract();
        dashboardContract.setUserId(userId);

        DashboardLayoutService dashboardLayoutService = new DashboardLayoutService(userId);
        DashboardLayout dashboardLayout = dashboardLayoutService.get();
        if (dashboardLayout != null) {
            dashboardContract.setLayout(dashboardLayout.getContent());
        }

        TileService<Tile> tileService = new TileService<>(userId, Tile.class);
        List<Tile> tiles = tileService.retrieveTiles();
        if (tiles.size() > 0) {

            for (Tile tile :
                    tiles) {
                Tile specificTile = null;
                switch (tile.getTemplateId()) {
                    case "twitter":
                        specificTile = getSpecificTile(userId, tile, TwitterTile.class);
                        break;
                    case "oneclick":
                        specificTile = getSpecificTile(userId, tile, OneClickTile.class);
                        break;
                    case "news":
                        specificTile = getSpecificTile(userId, tile, NewsTile.class);
                        break;
                    case "weather":
                        specificTile = getSpecificTile(userId, tile, WeatherTile.class);
                        break;
                    case "jira":
                        specificTile = getSpecificTile(userId, tile, JiraTile.class);
                        break;
                }

                dashboardContract.getTiles().add(specificTile);
            }
        }

        return Response.ok().entity(dashboardContract).build();
    }

    private <T extends Tile> T getSpecificTile(String userId, Tile tile, Class<T> tileClass) {
        TileService<T> tileService = new TileService<>(userId, tileClass);
        return tileService.getTile(userId, tile.getId());
    }


}
