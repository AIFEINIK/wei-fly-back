package com.wei.fly.util.httpclient;

import java.io.IOException;

public interface EntityCallback<T> extends Callback<T> {

    void onSuccess(T t) throws IOException;

}
