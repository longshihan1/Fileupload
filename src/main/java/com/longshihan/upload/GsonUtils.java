package com.longshihan.upload;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtils {
    public static final Gson gson = new Gson();

    public static String toJsonString(Object object){
        return object==null?null:gson.toJson(object);
    }
    public static Object JSONToObject(String json,Class beanClass) {
        Object res = gson.fromJson(json, beanClass);
        return res;
    }

    public static Object JSONToObject(String json, Type beanClass) {
        Object res = gson.fromJson(json, beanClass);
        return res;
    }



    public static Gson getGson(){
        return gson;
    }
}
