package com.bdjplus.webserver.service;

import com.bdjplus.webserver.response.Response;
import com.bdjplus.webserver.request.Request;

public class CreditsService implements IService {
    public Response handle(Request request) throws Exception {
        return new Response("BDJPlus created by Euro Ali<br>" +
                "Special Thanks to TheOfficialFloW for BD-JB (https://github.com/TheOfficialFloW/bd-jb/tree/master)<br>" +
                "Some BD-JB classes used from sleirsgoevy");
    }

    public boolean isMatch(String path) {
        return path.equals("/bdj/credits");
    }
}
