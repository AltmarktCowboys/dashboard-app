package com.spacecowboys.codegames.dashboardapp.model.dashboard;

import com.spacecowboys.codegames.dashboardapp.cache.CacheProvider;
import com.spacecowboys.codegames.dashboardapp.cache.LifeSpan;
import com.spacecowboys.codegames.dashboardapp.cache.RedisCache;

/**
 * Created by EDraser on 26.04.17.
 */
public class DashboardLayoutService {

    private String userId;
    private RedisCache<String, DashboardLayout> cache;

    public DashboardLayoutService(String userId) {
        this.userId = userId;
    }

    private RedisCache<String, DashboardLayout> getCache() {
        if (cache == null) {
            cache = CacheProvider.getCache("dashboard_layout", DashboardLayout.class, String.class);
        }

        return cache;
    }

    public DashboardLayout get() {

        RedisCache<String, DashboardLayout> cache = getCache();
        return cache.get(userId);
    }

    public void put(DashboardLayout dashboardLayout) {
        RedisCache<String, DashboardLayout> cache = getCache();
        cache.put(userId, dashboardLayout, LifeSpan.DURABLE);
    }

    public void remove() {
        RedisCache<String, DashboardLayout> cache = getCache();
        cache.remove(userId);
    }
}
