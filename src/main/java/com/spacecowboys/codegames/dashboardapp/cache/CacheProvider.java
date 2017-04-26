package com.spacecowboys.codegames.dashboardapp.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EDraser on 26.04.17.
 */
public class CacheProvider {
    private static Map<String, RedisCache> caches = new HashMap<>();

    public static Map<String, RedisCache> getCaches() {
        return caches;
    }

    private static <Payload> String getKey(String name, Class<Payload> clsKey) {

        return String.format("%s_%s", name, clsKey.getSimpleName());
    }

    public static <Key, Payload> RedisCache<Key, Payload> getCache(String name, Class<Payload> cls, Class<Key> clsKey) {
        RedisCache<Key, Payload> cache = getCaches().get(getKey(name, cls));

        if (cache == null) {
            cache = new RedisCache<>();
            cache.setName(name, cls, clsKey);
            getCaches().put(getKey(name, cls), cache);
        }

        return cache;
    }
}
