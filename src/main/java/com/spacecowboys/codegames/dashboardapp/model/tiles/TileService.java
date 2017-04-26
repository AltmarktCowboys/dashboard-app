package com.spacecowboys.codegames.dashboardapp.model.tiles;

import com.spacecowboys.codegames.dashboardapp.cache.CacheProvider;
import com.spacecowboys.codegames.dashboardapp.cache.LifeSpan;
import com.spacecowboys.codegames.dashboardapp.cache.RedisCache;
import com.spacecowboys.codegames.dashboardapp.cache.RedisInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDraser on 26.04.17.
 */
public class TileService<T extends Tile> {

    private static String tilePattern = "%s_%s";
    private String userId;
    private Class<T> tileClass;
    private RedisCache<String, T> cache;
    private RedisCache<String, String> contentCache;

    public TileService(String userId, Class<T> tileClass) {
        this.userId = userId;
        this.tileClass = tileClass;
    }

    private RedisCache<String, T> getCache() {
        if (cache == null) {
            cache = CacheProvider.getCache("tiles", this.tileClass, String.class);
        }

        return cache;
    }

    private RedisCache<String, String> getContentCache() {
        if (contentCache == null) {
            contentCache = CacheProvider.getCache("tile_content", String.class, String.class);
        }

        return contentCache;
    }

    public List<T> retrieveTiles() {

        List<T> result = new ArrayList<>();
        RedisCache<String, T> redisCache = getCache();

        String pattern = String.format(tilePattern, userId, "");
        return redisCache.list(pattern);
    }

    public void putTile(T tile) {
        RedisCache<String, T> redisCache = getCache();
        String key = getKey(tile);
        redisCache.put(key, tile, LifeSpan.DURABLE);
    }

    private String getKey(T tile) {
        return getKey(tile.getId());
    }

    private String getKey(String tileId) {
        return String.format(tilePattern, userId, tileId);
    }

    public T getTile(String userId, String tileId) {
        RedisCache<String, T> redisCache = getCache();
        String key = getKey(tileId);

        return redisCache.get(key);
    }

    public void removeTile(String tileId) {
        RedisCache<String, T> redisCache = getCache();
        String key = getKey(tileId);

        redisCache.remove(key);
    }

    public String getTileContent(String tileId) {

        RedisCache<String, String> contentCache = getContentCache();
        return contentCache.get(getKey(tileId));
    }

    public void setTileContent(String tileId, String jsonContent) {
        RedisCache<String, String> contentCache = getContentCache();
        contentCache.put(getKey(tileId), jsonContent, LifeSpan.SHORT);
    }
}
