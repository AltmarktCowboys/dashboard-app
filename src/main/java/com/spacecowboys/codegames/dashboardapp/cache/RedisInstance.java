package com.spacecowboys.codegames.dashboardapp.cache;

import org.jboss.logging.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

/**
 * Created by EDraser on 25.04.17.
 */
public class RedisInstance {

    private static final Logger LOGGER = Logger.getLogger(RedisInstance.class);
    private static volatile RedisInstance INSTANCE;
    private volatile Pool<Jedis> jedisPool;

    //region singleton
    private RedisInstance() {
    }

    public static RedisInstance getInstance() {
        if (INSTANCE == null) {
            synchronized (RedisInstance.class) {
                if (INSTANCE == null) {
                    RedisInstance instance = new RedisInstance();
                    instance.initRedisInstance();
                    INSTANCE = instance;
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Returns the Redis pool as a list and if it's not initialized yet an instance of either
     * JedisSentinelPool, JedisPool or JedisCluster will be added to the pool list.
     *
     */
    private void initRedisInstance() {
        // Single host: "ip:port"
        // Multiple hosts: "firstIpHost:port secondIpHost:port thirdIpHost:port"
        // Not really a cluster solution (with replication), more like a weighted farm.
        String host = "localhost";
        int cachePort = 32768;

        if (cachePort == 0) {
            cachePort = 6379;
        }

        LOGGER.debug("redis server on host=" + host + " port=" + cachePort);
        HostAndPort hostInfo = getHostInfo(host, cachePort);
        jedisPool = new JedisPool(new JedisPoolConfig(), hostInfo.getHost(), hostInfo.getPort());
    }

    //endregion

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    /**
     * Closes the Redis pool respectively the JedisCluster instance.
     */
    public void close() {
        if (jedisPool != null) {
            try {
                jedisPool.close();
            } catch (Throwable e) {
                LOGGER.error("failed to close JedisPool", e);
            }
        }
    }


    private static HostAndPort getHostInfo(String host, int cachePort) {
        String[] split = host.split(":");

        if (split.length == 2) {
            return new HostAndPort(split[0], Integer.parseInt(split[1]));
        } else {
            if (split.length == 1) {
                return new HostAndPort(split[0], cachePort);
            } else {
                return null;
            }
        }
    }
}
