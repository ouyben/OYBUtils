package com.ouyben.androidutils.utils;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络工具类
 */
public class HttpUtil {
    /**
     * 成功
     */
    private static final int SUCCESS = 0;
    /**
     * 失败
     */
    private static final int FAILURE = 1;
    /**
     * 超时
     */
    private static final int TIMEOUT = 5000;


    private HttpUtil() {
          /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 用接口回调
     */
    public interface IResult {
        void onSuccess(String result);

        void onFailure(String desc);
    }


    /**
     * 通过抽象类 来传递消息
     */
    public static abstract class ResultHandler extends Handler implements IResult {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            switch (msg.what) {
                case SUCCESS:
                    if (result == null && "".equals(result)) {
                        onFailure("result is null");
                    } else {
                        onSuccess(result);
                    }
                    break;
                case FAILURE:
                    onFailure(result);
                    break;
            }
        }
    }

    ;

    /**
     * 网络请求 post
     */
    public static void doHttp(final String url, final String params,
                              final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter out = null;
                BufferedReader in = null;
                HttpURLConnection conn = null;
                try {
                    URL realUrl = new URL(url);
                    // 打开和URL之间的连接
                    conn = (HttpURLConnection) realUrl
                            .openConnection();
                    // 设置通用的请求属性
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setUseCaches(false);
                    // 发送POST请求必须设置如下两行
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setReadTimeout(TIMEOUT);
                    conn.setConnectTimeout(TIMEOUT);

                    if (params != null && !params.trim().equals("")) {
                        // 获取URLConnection对象对应的输出流
                        out = new PrintWriter(conn.getOutputStream());
                        // 发送请求参数
                        out.print(params);
                        // flush输出流的缓冲
                        out.flush();
                    }
                    // 定义BufferedReader输入流来读取URL的响应
                    in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        builder.append(line);
                    }
                    // 得到的服务器返回的值
                    String resultStr = new String(builder);
                    if (resultStr.length() > 0) {
                        handler.obtainMessage(SUCCESS, resultStr).sendToTarget();
                    } else {
                        handler.obtainMessage(FAILURE, "服务器返回null").sendToTarget();
                    }
                } catch (Exception e) {
                    try {
                        if (conn != null && conn.getResponseCode() == 404) {
                            handler.obtainMessage(FAILURE, "请求地址不正确,请您确认后重新操作!Code=" + conn.getResponseCode())
                                    .sendToTarget();
                        } else if (conn != null && conn.getResponseCode() >= 400 && conn.getResponseCode() < 500) {
                            handler.obtainMessage(FAILURE, "服务器异常,请稍后操作!Code=" + conn.getResponseCode())
                                    .sendToTarget();
                        } else if (conn != null && conn.getResponseCode() >= 500) {
                            handler.obtainMessage(FAILURE, "客户端异常,请重启后再试!Code=" + conn.getResponseCode())
                                    .sendToTarget();
                        } else if (conn != null) {
                            handler.obtainMessage(FAILURE, "未知异常!Code=" + conn.getResponseCode())
                                    .sendToTarget();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
                // 使用finally块来关闭输出流、输入流
                finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }).start();

    }


    /**
     * 网络请求 get
     */
    public static void doHttp(final String url, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                try {
                    URL realUrl = new URL(url);
                    conn = (HttpURLConnection) realUrl.openConnection();
                    conn.setReadTimeout(TIMEOUT);
                    conn.setConnectTimeout(TIMEOUT);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        int len = -1;
                        byte[] buf = new byte[128];

                        while ((len = is.read(buf)) != -1) {
                            baos.write(buf, 0, len);
                        }
                        baos.flush();
                        if (baos.toString().length() > 0) {
                            handler.obtainMessage(SUCCESS, baos.toString()).sendToTarget();
                        } else {
                            handler.obtainMessage(FAILURE, "服务器返回null").sendToTarget();
                        }
                    } else if (conn.getResponseCode() == 404) {
                        handler.obtainMessage(FAILURE, "请求地址不正确,请您确认后重新操作!Code=" + conn.getResponseCode())
                                .sendToTarget();
                    } else if (conn.getResponseCode() >= 400 && conn.getResponseCode() < 500) {
                        handler.obtainMessage(FAILURE, "服务器异常,请稍后操作!Code=" + conn.getResponseCode())
                                .sendToTarget();
                    } else if (conn.getResponseCode() >= 500) {
                        handler.obtainMessage(FAILURE, "客户端异常,请重启后再试!Code=" + conn.getResponseCode())
                                .sendToTarget();
                    } else {
                        handler.obtainMessage(FAILURE, "未知异常!Code=" + conn.getResponseCode())
                                .sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (baos != null)
                            baos.close();
                    } catch (IOException e) {
                    }
                    conn.disconnect();
                }
            }
        }).start();

    }


}
