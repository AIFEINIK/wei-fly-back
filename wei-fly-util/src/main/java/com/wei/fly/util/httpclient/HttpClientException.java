package com.wei.fly.util.httpclient;

import java.io.IOException;

public class HttpClientException extends IOException {

    private static final long serialVersionUID = -7773120827199778286L;

    public HttpClientException() {
    }

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpClientException(Throwable cause) {
        super(cause);
    }

}
