package com.spacecowboys.codegames.dashboardapp.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * Created by EDraser on 26.04.17.
 */
public class JSON {

    private static ObjectMapper mapper = null;

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            synchronized (JSON.class) {
                if (mapper == null) {
                    mapper = new ObjectMapper();
                    mapper.registerModule(new JaxbAnnotationModule());
                }
            }
        }
        return mapper;
    }

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

    static public <T> T read(byte[] data, Class<T> cls) throws IOException {
        return getMapper().readValue(data, cls);
    }
}
