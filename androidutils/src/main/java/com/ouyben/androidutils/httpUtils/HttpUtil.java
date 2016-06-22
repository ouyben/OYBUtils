package com.ouyben.androidutils.httpUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO :xutils网络请求工具类
 * Created by owen on 2016-04-22.
 */
public class HttpUtil {

    /**
     * 回调接口
     */
    public interface IHttpResult {

        void onSuccess(String result);

        void onError(Throwable ex, boolean isOnCallback);

        void onCancelled(String canceStr);

        void onFinished();

    }

    /**
     * 网络请求
     *
     * @param url
     * @param params
     * @param result
     */
    public static void doHttp(String url, HashMap<String, String> params, IHttpResult result) {
        if (params == null || params.size() == 0) {
            doGet(url, result);
        } else if (params.size() > 0) {
            doPost(url, params, result);
        }
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param iHttpResult
     */
    private static void doPost(String url, HashMap<String, String> params, final IHttpResult iHttpResult) {
        RequestParams requestParams = new RequestParams(url);
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            requestParams.addBodyParameter(key, val);
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                iHttpResult.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                iHttpResult.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                iHttpResult.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                iHttpResult.onFinished();
            }
        });


    }

    /**
     * get 请求
     *
     * @param url
     * @param iHttpResult
     */
    private static void doGet(String url, final IHttpResult iHttpResult) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                iHttpResult.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                iHttpResult.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                iHttpResult.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
                iHttpResult.onFinished();
            }
        });
    }

}
