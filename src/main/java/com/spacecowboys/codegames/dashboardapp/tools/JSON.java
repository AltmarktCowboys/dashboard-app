package com.spacecowboys.codegames.dashboardapp.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.spacecowboys.codegames.dashboardapp.cache.RedisObject;
import redis.clients.util.SafeEncoder;

/**
 * Created by EDraser on 26.04.17.
 */
public class JSON {

    public static <T> T fromString(String content, Class<T> tClass) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        T object = gson.fromJson(content, tClass);

        return object;
    }

    public static <T> String toString(T object, Class<T> tClass) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().serializeNulls();
        String jsonObject = gsonBuilder.create().toJson(object, tClass);

        return jsonObject;
    }
}
