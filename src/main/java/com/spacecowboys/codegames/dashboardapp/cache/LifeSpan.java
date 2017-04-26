package com.spacecowboys.codegames.dashboardapp.cache;

/**
 * Created by EDraser on 26.04.17.
 */
/**
 * Determine how long a entry stays in cache
 */
public enum LifeSpan {
    /**
     * Store object for 30 Seconds
     */
    SHORT,
    /**
     * Store object for 100 Minutes
     */
    LONG,
    /**
     * Store object for 100 Hours
     */
    PERSISTENT,
    /**
     * Store object until the next flush
     */
    DURABLE
}
