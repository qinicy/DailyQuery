package com.qinicy.tools.data.api;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by qinicy on 16/2/3.
 */
public class ApiClient {

    private final String TAG = "ApiClient";
    private final static String SCHEME= "http";
    private final static String HOST= "10.120.80.84";
    private final static String PORT = "8080";
    private final static String PATH = "/DataService/nn/data/";
    public final static String URL = SCHEME+"://"+HOST+":"+PORT+PATH;
    public void get(String url, Map<String,String> params, IApiCallBack callBack) {

        OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .build()
                .connTimeOut(10000)
                .execute(callBack);

    }
    public <T> T getApiResponse(String json,Type typeOfT){
        Gson gson = new Gson();

        T responseT = gson.fromJson(json,typeOfT);

        return responseT;
    }
}
