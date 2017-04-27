package com.spacecowboys.codegames.dashboardapp.model.jira;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.jboss.logging.Logger;


/**
 * Created by EDraser on 27.04.17.
 */
public class JiraService {

    private static final Logger LOGGER = Logger.getLogger(JiraService.class);

    private JiraTile tile;
    private String userId;

    public JiraService(JiraTile tile, String userId) {
        this.tile = tile;
        this.userId = userId;
    }

    public JiraContent getContent() {

        JiraContent content = null;
        TileService<JiraTile> tileService = new TileService<>(tile.getUserId(), JiraTile.class);
        String cachedContent = tileService.getTileContent(tile.getId());
        if (!Strings.isNullOrEmpty(cachedContent)) {
            content = JSON.fromString(cachedContent, JiraContent.class);
        } else {
            content = new JiraContent();

            try {

            } catch (Throwable e) {
                LOGGER.error("failed to load jira issues", e);
            }

            tileService.setTileContent(tile.getId(), JSON.toString(content, JiraContent.class));
        }

        return content;
    }
}
