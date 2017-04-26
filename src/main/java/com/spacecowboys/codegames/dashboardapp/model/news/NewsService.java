package com.spacecowboys.codegames.dashboardapp.model.news;

import com.google.common.base.Strings;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.spacecowboys.codegames.dashboardapp.model.tiles.TileService;
import com.spacecowboys.codegames.dashboardapp.tools.DateTimeTools;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.jboss.logging.Logger;

import java.net.URL;

/**
 * Created by EDraser on 26.04.17.
 */
public class NewsService {

    private String userId;
    private NewsTile tile;
    private static final String NEWS_URI = "https://news.google.de/news?cf=all&hl=de&pz=1&ned=de&output=rss";
    private static final Logger LOGGER = Logger.getLogger(NewsService.class);

    public NewsService(String userId, NewsTile tile) {
        this.userId = userId;
        this.tile = tile;
    }

    public NewsContent getContent() {

        NewsContent content = null;
        TileService<NewsTile> tileService = new TileService<>(tile.getUserId(), NewsTile.class);
        String cachedContent = tileService.getTileContent(tile.getId());
        if (!Strings.isNullOrEmpty(cachedContent)) {
            content = JSON.fromString(cachedContent, NewsContent.class);
        } else {
            content = new NewsContent();

            try {
                URL feedUrl = new URL(Strings.isNullOrEmpty(tile.getNewsUrl()) ? NEWS_URI : tile.getNewsUrl());
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                content.setTitle(feed.getTitle());
                content.setLink(feed.getLink());
                content.setPubDate(DateTimeTools.toLocalDateTime(feed.getPublishedDate()));
                if (feed.getImage() != null) {
                    content.setImageUrl(feed.getImage().getUrl());
                }

                for (SyndEntry entry :
                        feed.getEntries()) {
                    FeedItem feedItem = new FeedItem();
                    if (entry.getCategories().size() > 0) {
                        feedItem.setCategory(entry.getCategories().get(0).getName());
                    }
                    if (entry.getCategories() != null) {
                        // wegen Performance auskommentiert
                        // feedItem.setHtmlDescription(entry.getDescription().getValue());
                    }
                    feedItem.setLink(entry.getLink());
                    feedItem.setTitle(entry.getTitle());
                    feedItem.setPubDate(DateTimeTools.toLocalDateTime(entry.getPublishedDate()));

                    content.getFeeds().add(feedItem);
                }

            } catch (Throwable e) {
                LOGGER.error("failed to load rss feed", e);
            }

            tileService.setTileContent(tile.getId(), JSON.toString(content, NewsContent.class));
        }

        return content;
    }
}
