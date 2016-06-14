package com.ouyben.androidutils.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description Gson工具类
 */
public class GsonUtil {

    private static volatile GsonUtil sGsonUtil;
    private Gson mGson;

    public GsonUtil getGsonUtil() {
        if (sGsonUtil == null) {
            synchronized (GsonUtil.class) {
                if (sGsonUtil == null)
                    sGsonUtil = new GsonUtil();
            }
        }
        return sGsonUtil;
    }

    private GsonUtil() {
        mGson = new Gson();
    }

    public <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            if (mGson != null)
                t = mGson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public <T> List<T> getBeans(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            if (mGson != null)
                list = mGson.fromJson(jsonString, new TypeToken<List<T>>() {
                }.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public <T> List<T> getBeans2(String jsonString, Class<T[]> cls) {
        T[] ts = null;
        try {
            if (mGson != null) {
                ts = mGson.fromJson(jsonString, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(ts);
    }

    public List<String> getList(String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            list = mGson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            // TODO: handle exception  
        }
        return list;

    }

    public List<Map<String, Object>> listKeyMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = mGson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            // TODO: handle exception  
        }
        return list;
    }
}
