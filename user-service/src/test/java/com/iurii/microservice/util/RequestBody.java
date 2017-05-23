package com.iurii.microservice.util;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestBody extends JSONObject {

    private static final String name = "name";
    private static final String birthDate = "birthDate";
    private static final String updatedTime = "updatedTime";
    private static final String money = "money";

    public RequestBody() {}

    public RequestBody setName(String name) throws JSONException {
        this.put(this.name, name);
        return this;
    }

    public RequestBody birthDate(String birthDate) throws JSONException {
        this.put(this.birthDate, birthDate);
        return this;
    }

    public RequestBody setUpdatedTime(String updatedTime) throws JSONException {
        this.put(this.updatedTime, updatedTime);
        return this;
    }

    public RequestBody setMoney(String money) throws JSONException {
        this.put(this.money, money);
        return this;
    }

}
