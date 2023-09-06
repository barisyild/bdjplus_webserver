package com.bdjplus.webserver.request;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Request {
    public String method;
    public String path;
    public int contentLength;
    public Map headers;
    public byte[] bodyBytes;
    // Optimization
    private String _bodyString = null;

    public String getBodyString() {
        if(_bodyString != null)
            return _bodyString;

        return _bodyString = new String(bodyBytes);
    }
}
