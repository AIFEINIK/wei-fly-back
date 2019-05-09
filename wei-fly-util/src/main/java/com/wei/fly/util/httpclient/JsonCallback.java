package com.wei.fly.util.httpclient;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public interface JsonCallback extends Callback {

    void onSuccess(JSONObject json) throws IOException;

}
