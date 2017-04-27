package com.spacecowboys.codegames.dashboardapp.model.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.jboss.logging.Logger;

import java.net.URI;


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
                JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
                URI uri = new URI(tile.getJiraUrl());
                JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, tile.getUsername(), tile.getPassword());

                Promise<User> userPromise = client.getUserClient().getUser(tile.getUsername());
                User user = userPromise.claim();

                String query = String.format("assignee = %s AND status != Done ORDER BY priority DESC, updated DESC", tile.getUsername());
                Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql(query);

                for (Issue issue : searchJqlPromise.claim().getIssues()) {
                    JiraIssue jiraIssue = new JiraIssue();
                    jiraIssue.setKey(issue.getKey());
                    jiraIssue.setPriority(issue.getPriority().getName());
                    jiraIssue.setState(issue.getStatus().getDescription());
                    jiraIssue.setSummary(issue.getSummary());
                    jiraIssue.setUrl(issue.getSelf().toString());
                }

            } catch (Throwable e) {
                LOGGER.error("failed to load jira issues", e);
            }

            tileService.setTileContent(tile.getId(), JSON.toString(content, JiraContent.class));
        }

        return content;
    }
}
