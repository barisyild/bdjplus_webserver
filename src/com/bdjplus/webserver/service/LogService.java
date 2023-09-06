package com.bdjplus.webserver.service;

import com.bdjplus.webserver.response.Response;
import com.sony.bdjstack.system.BDJModule;
import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.util.StringUtil;

public class LogService implements IService {

    public Response handle(Request request) throws Exception {
        return new Response(StringUtil.replace(BDJModule.log(), "\n", "<br>"));
    }

    public boolean isMatch(String path) {
        return path.equals("/bdj/log");
    }
}
