package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.jboss.logging.Logger;

/**
 * Created by EDraser on 26.04.17.
 */
public class OneClickService {

    private static final Logger LOGGER = Logger.getLogger(OneClickService.class);

    public OneClickContent getOneClickContent(OneClickTile tile) {

        OneClickContent oneClickContent = null;
        TileService<OneClickTile> tileService = new TileService<>(tile.getUserId(), OneClickTile.class);
        String cachedContent = tileService.getTileContent(tile.getId());
        if (!Strings.isNullOrEmpty(cachedContent)) {
            oneClickContent = JSON.fromString(cachedContent, OneClickContent.class);
        } else {
            oneClickContent = loadOneClickContent(tile);
            if(oneClickContent != null) {
                tileService.setTileContent(tile.getId(), JSON.toString(oneClickContent, OneClickContent.class));
            }
        }

        return oneClickContent;
    }

    private OneClickContent loadOneClickContent(OneClickTile oneClickTile) {

        try {
            OneClickCredentials oneClickCredentials =
                    new OneClickCredentials(oneClickTile.getAccessNumber(),
                            oneClickTile.getUsername(),
                            oneClickTile.getPassword(),
                            oneClickTile.getUri());
            oneClickCredentials.auth();
            
            OneClickPrincipal oneClickPrincipal = OneClickPrincipal.load(oneClickCredentials);

            OneClickContent oneClickContent = new OneClickContent();
            oneClickContent.setEmail(oneClickPrincipal.getEmail());
            oneClickContent.setId(oneClickPrincipal.getId());
            oneClickContent.setMemberType(oneClickPrincipal.getMemberType());
            oneClickContent.setOrganizationId(oneClickPrincipal.getOrganizationId());
            oneClickContent.setLoginName(oneClickPrincipal.getLoginName());
            oneClickContent.setSessionId(oneClickPrincipal.getSessionId());
            oneClickContent.setDirectLink(oneClickPrincipal.getDirectLink());

            return oneClickContent;

        } catch (Exception e) {
            LOGGER.debug("could not load OneClickContent", e);
        }
        return null;
    }

}
