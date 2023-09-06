package com.bdjplus.webserver.exception;

public class NotSupportedRequestMethodException extends Exception {
    public NotSupportedRequestMethodException(String requestMethod) {
        super(requestMethod);
    }
}
