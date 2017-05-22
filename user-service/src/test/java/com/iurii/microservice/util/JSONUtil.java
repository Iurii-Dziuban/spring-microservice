package com.iurii.microservice.util;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class JSONUtil {

    private JSONUtil() {}

    public static String getResponseLink(String responseEntity) throws JSONException {
        JSONObject links = new JSONObject(responseEntity).getJSONObject("_links");
        JSONObject self = links.getJSONObject("self");
        return self.getString("href");
    }

    public static JSONObject getResponseMoneyObject(String responseEntity) throws JSONException {
        return new JSONObject(responseEntity).getJSONObject("money");
    }

    public static long getResponseMoney(String responseEntity) throws JSONException, IOException {
        return getResponseMoneyObject(responseEntity).getLong("value");
    }
}
