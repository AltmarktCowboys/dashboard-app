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

        OneClickContent session = null;
        TileService<OneClickTile> tileService = new TileService<>(tile.getUserId(), OneClickTile.class);
        String cachedContent = tileService.getTileContent(tile.getId());
        if (!Strings.isNullOrEmpty(cachedContent)) {
            session = JSON.fromString(cachedContent, OneClickContent.class);
        } else {
            OneClickPrincipal oneClickPrincipal = loadOneClickPrincipal(tile);
            if(oneClickPrincipal != null) {
                session = new OneClickContent();
                session.setEmail(oneClickPrincipal.getEmail());
                session.setId(oneClickPrincipal.getId());
                session.setMemberType(oneClickPrincipal.getMemberType());
                session.setOrganizationId(oneClickPrincipal.getOrganizationId());
                session.setLoginName(oneClickPrincipal.getLoginName());
                session.setSessionId(oneClickPrincipal.getSessionId());

                tileService.setTileContent(tile.getId(), JSON.toString(session, OneClickContent.class));
            }

        }

        return session;
    }

    private OneClickPrincipal loadOneClickPrincipal(OneClickTile oneClickTile) {

        try {
            OneClickCredentials oneClickCredentials =
                    new OneClickCredentials(oneClickTile.getAccessNumber(),
                            oneClickTile.getUsername(),
                            oneClickTile.getPassword(),
                            oneClickTile.getUri());
            oneClickCredentials.auth();
            OneClickPrincipal oneClickPrincipal = OneClickPrincipal.load(oneClickCredentials);
            return oneClickPrincipal;
        } catch (Exception e) {
            LOGGER.debug("could not load OneClickPrincipal", e);
        }
        return null;
    }
}
