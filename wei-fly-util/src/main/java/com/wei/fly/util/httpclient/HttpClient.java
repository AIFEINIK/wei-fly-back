package com.wei.fly.util.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class HttpClient implements InitializingBean {

    private static final String GET = "GET";
    private static final String POST = "POST";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;

    @Value("${okhttp.minThreadPool:5}")
    private int minThreadPool;

    @Value("${okhttp.maxThreadPool:30}")
    private int maxThreadPool;

    @Value("${okhttp.maxRequests:1000}")
    private int maxRequests;

    @Value("${okhttp.maxRequestsPerHost:100}")
    private int maxRequestsPerHost;

    @Value("${okhttp.connectTimeout:10000}")
    private long connectTimeout;

    @Value("${okhttp.readTimeout:10000}")
    private long readTimeout;

    private void init() throws Exception {
        httpClient = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .dispatcher(new Dispatcher(new ThreadPoolExecutor(minThreadPool, maxThreadPool,
                        60, TimeUnit.SECONDS, new LinkedBlockingQueue<>())))
                .build();
        httpClient.dispatcher().setMaxRequests(maxRequests);
        httpClient.dispatcher().setMaxRequestsPerHost(maxRequestsPerHost);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public String get(String url) throws IOException {
        return execute(GET, url, null, null);
    }

    public void getAsync(String url, Callback callback) {
        executeAsync(GET, url, null, null, callback);
    }

    public String postJson(String url, Object json) throws IOException {
        return postJson(url, JSON.toJSONString(json));
    }

    public String postJson(String url, String json) throws IOException {
        return execute(POST, url, null, RequestBody.create(MEDIA_TYPE_JSON, json));
    }

    public <T> void postJsonAsync(String url, Object json, Callback<T> callback) {
        executeAsync(POST, url, null, RequestBody.create(MEDIA_TYPE_JSON, JSON.toJSONString(json)), callback);
    }

    public String postForm(String url, Map<String, String> params) throws IOException {
        return execute(POST, url, null, buildFormRequestBody(params));
    }

    public <T> void postFormAsync(String url, Map<String, String> params, Callback<T> callback) {
        executeAsync(POST, url, null, buildFormRequestBody(params), callback);
    }

    private String execute(String method, String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        return httpClient.newCall(buildRequest(method, url, headers, requestBody)).execute().body().string();
    }

    private <T> void executeAsync(String method, String url, Map<String, String> headers, RequestBody requestBody, Callback<T> callback) {
        httpClient.newCall(buildRequest(method, url, headers, requestBody)).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String body = response.body().string();
                    if (callback instanceof StringCallback) {
                        ((StringCallback) callback).onSuccess(body);
                    } else if (callback instanceof JsonCallback) {
                        ((JsonCallback) callback).onSuccess(JSONObject.parseObject(body));
                    } else if (callback instanceof EntityCallback) {
                        ((EntityCallback<T>) callback).onSuccess(JSONObject.parseObject(body, getInterfaceGenericType(callback)));
                    } else {
                        throw new HttpClientException("Invalid Callback");
                    }
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }

        });
    }

    private static Request buildRequest(String method, String url, Map<String, String> headers, RequestBody requestBody) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method(method, requestBody);
        if (headers != null && headers.size() > 0) {
            headers.forEach((key, value) -> requestBuilder.addHeader(key, value));
        }
        return requestBuilder.build();
    }

    private static RequestBody buildFormRequestBody(Map<String, String> params) {
        if (params == null || params.size() <= 0) {
            return null;
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        params.forEach((key, value) -> formBodyBuilder.add(key, value));
        return formBodyBuilder.build();
    }

    private <T> Class<T> getInterfaceGenericType(Callback<T> callback) throws Exception {
        Type type = ((ParameterizedType) callback.getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
        Field field = type.getClass().getDeclaredField("rawType");
        field.setAccessible(true);
        return (Class<T>) field.get(type);
    }

}
