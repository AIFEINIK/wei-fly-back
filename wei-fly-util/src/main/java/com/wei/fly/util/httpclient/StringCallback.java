package com.wei.fly.util.httpclient;

import java.io.IOException;

public interface StringCallback extends Callback {

    void onSuccess(String body) throws IOException;

}
