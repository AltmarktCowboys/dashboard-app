package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.cache.RedisCache;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;

import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
public class OneClickService {

    public OneClickSession getOneClickSession(OneClickTile tile) {

        OneClickSession session = null;
        TileService<OneClickTile> tileService = new TileService<>(tile.getUserId(), OneClickTile.class);
        String cachedContent = tileService.getTileContent(tile.getId());
        if (Strings.isNullOrEmpty(cachedContent)) {
            session = JSON.fromString(cachedContent, OneClickSession.class);
        } else {
            session = new OneClickSession();
            session.setEmail("edwin.draser@wolterskluwer.com");
            session.setId(UUID.randomUUID().toString());
            session.setMemberType("client_postmaster");
            session.setOrganizationId(UUID.randomUUID().toString());
            session.setLoginName("edraser");
            session.setSdnEnvironmentUrl("https://moveon.sdn.two-clicks.de");
            session.setSessionId(UUID.randomUUID().toString());
        }

        return session;
    }
}
