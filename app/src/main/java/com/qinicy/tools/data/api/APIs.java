package com.qinicy.tools.data.api;

import com.qinicy.tools.common.Configs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinicy on 16/2/25.
 */
public class APIs implements ICheckPhoneNumApi {
    private ApiClient apiClient;
    private volatile static APIs instance;

    private APIs() {
        super();
        apiClient = new ApiClient();

    }

    public static APIs getInstance() {
        if (instance == null) {
            synchronized (APIs.class) {
                if (instance == null) {
                    instance = new APIs();

                }
            }
        }
        return instance;
    }

    @Override
    public void checkPhoneNum(int number, IApiCallBack listener) {

        Map<String,String> params = new HashMap<>();
        params.put("phone",String.valueOf(number));
        params.put("key",Configs.CHECK_PHONE_NUM_APP_KEY);
        if (apiClient!=null)
            apiClient.get(Configs.CHECK_PONE_NUM_URL,params,listener);
    }
}
