package com.spacecowboys.codegames.dashboardapp.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.util.SafeEncoder;

/**
 * Created by EDraser on 26.04.17.
 */
public class RedisCache<Key, Payload> {

    private Class cls;
    private Class clsKey;
    private String namespace;

    public RedisCache() {
    }

    public void setName(String namespace, Class cls, Class clsKey) {
        this.cls = cls;
        this.clsKey = clsKey;
        this.namespace = namespace;
    }

    public void put(Key key, final Payload payload, LifeSpan span) {
        if (!cls.isAssignableFrom(payload.getClass())) {
            throw new RuntimeException(payload.getClass() + " is not of " + cls);
        }

        byte[] keyBytes = getKeyBytes(key);
        final int expire = calculateExpire(span);

        RedisObject o = new RedisObject(key, payload, expire);
        byte[] payloadBytes = getPayloadBytes(o);

        try (Jedis redisResource = RedisInstance.getInstance().getResource()) {
            Pipeline pipelined = redisResource.pipelined();
            pipelined.set(keyBytes, payloadBytes);
            pipelined.expire(keyBytes, expire);
            pipelined.sync();
        }
    }

    public void putAssumeEmpty(Key key, final Payload payload, LifeSpan span) {
        if (!cls.isAssignableFrom(payload.getClass())) {
            throw new RuntimeException(payload.getClass() + " is not of " + cls);
        }

        byte[] keyBytes = getKeyBytes(key);
        final int expire = calculateExpire(span);

        RedisObject o = new RedisObject(key, payload, expire);
        byte[] payloadBytes = getPayloadBytes(o);

        try (Jedis redisResource = RedisInstance.getInstance().getResource()) {
            if (redisResource.setnx(keyBytes, payloadBytes) == 0) {
                throw new RuntimeException("key " + key.toString() + " already exists");
            }

            redisResource.expire(keyBytes, expire);
        }
    }

    private int calculateExpire(LifeSpan span) {
        int expire;
        switch (span) {
            case SHORT:
                // 30 sec
                expire = 30;
                break;
            case LONG:
                // 100 min
                expire = 6000;
                break;
            case PERSISTENT:
                // 100 h
                expire = 360000;
                break;
            case DURABLE:
                // until next flush
                expire = Integer.MAX_VALUE;
                break;
            default:
                expire = 360000;

        }
        return expire;
    }

    private byte[] getPayloadBytes(RedisObject payload) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonObject = gsonBuilder.create().toJson(payload);

        return SafeEncoder.encode(jsonObject);
    }

    private byte[] getKeyBytes(Key key) {
        String bf = String.format("%s#%s", namespace, key.toString());
        return SafeEncoder.encode(bf);
    }

    private byte[] getNamespacesQueryString() {
        String bf = String.format("%s#*", namespace);
        return SafeEncoder.encode(bf);
    }

    private RedisObject deserializeFromBytes(byte[] payloadBytes) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        TypeToken typeToken = TypeToken.getParameterized(RedisObject.class, clsKey, cls);
        RedisObject<Key, Payload> redisObject = gson.fromJson(SafeEncoder.encode(payloadBytes),
                typeToken.getType());

        return redisObject;
    }

    public void remove(Key key) {
        byte[] keyBytes = getKeyBytes(key);

        try (Jedis redisResource = RedisInstance.getInstance().getResource()){
            redisResource.del(keyBytes);
        }
    }

    public Payload get(Key key) {
        byte[] keyBytes = getKeyBytes(key);
        RedisObject o = null;

        try (Jedis redisResource = RedisInstance.getInstance().getResource()) {
            byte[] bytes = redisResource.get(keyBytes);

            if (bytes != null) {
                o = deserializeFromBytes(bytes);
                redisResource.expire(keyBytes, o.getExpires());
            }
        } catch (Exception e) {
            // LOGGER.error(e.getMessage(), e);
        }

        return o != null ? (Payload) o.getValue() : null;
    }

    public Payload peek(Key key) {
        byte[] keyBytes = getKeyBytes(key);
        RedisObject o = null;

        try (Jedis redisResource = RedisInstance.getInstance().getResource()) {
            byte[] bytes = redisResource.get(keyBytes);
            if (bytes != null) {
                o = deserializeFromBytes(bytes);
            }
        } catch (Exception e) {
            // LOGGER.error(e.getMessage(), e);
        }

        return o != null ? (Payload) o.getValue() : null;
    }
}
