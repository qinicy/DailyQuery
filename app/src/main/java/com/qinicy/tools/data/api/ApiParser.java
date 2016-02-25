package com.qinicy.tools.data.api;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by qinicy on 16/2/2.
 */
public class ApiParser {
    private volatile static ApiParser instance;
    private ApiParser() {
        super();

    }

    public static ApiParser getInstance() {
        if (instance == null) {
            synchronized (ApiParser.class) {
                if (instance == null) {
                    instance = new ApiParser();

                }
            }
        }
        return instance;
    }

    public <T> T parseResponseFrom(String json,Type typeOfT){
        Gson gson = new Gson();
        T responseT = gson.fromJson(json,typeOfT);
        return responseT;
    }

   
}
