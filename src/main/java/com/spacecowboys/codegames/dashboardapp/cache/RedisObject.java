package com.spacecowboys.codegames.dashboardapp.cache;

/**
 * Created by EDraser on 26.04.17.
 */
public class RedisObject<Key, Payload> {

    private Payload value;
    private int expires;
    private Key key;

    public RedisObject() {
    }

    public RedisObject(Key key, Payload value, int expires) {
        this.key = key;
        this.value = value;
        this.expires = expires;
    }

    public Key getKey() {
        return key;
    }

    public Payload getValue() {
        return value;
    }

    public int getExpires() {
        return expires;
    }
}
