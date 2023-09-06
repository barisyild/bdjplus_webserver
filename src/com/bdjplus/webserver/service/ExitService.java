package com.bdjplus.webserver.service;

import com.bdjplus.webserver.response.Response;
import com.sony.bdjstack.system.BDJModule;
import com.bdjplus.webserver.request.Request;

public class ExitService implements IService {
    public Response handle(Request request) throws Exception {
        //BDJModule.getInstance().prepareTitle("");

        // Crash process for exit from application
        BDJModule.getInstance().prepareTitle(null);
        return null;
    }

    public boolean isMatch(String path) {
        return path.equals("/bdj/exit");
    }
}
