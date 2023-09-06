package com.bdjplus.webserver.service;

import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.response.Response;

public class ResourceService implements IService {

    public Response handle(Request request) throws Exception {

        return new Response("implement required");
    }

    public boolean isMatch(String path) {
        return path.equals("/bdj/system/resources");
    }
}
