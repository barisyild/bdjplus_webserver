package com.bdjplus.webserver.service;

import com.bdjplus.webserver.response.Response;
import com.bdjplus.webserver.request.Request;

public class VariablesService implements IService {
    public Response handle(Request request) throws Exception {
        String stringBuilder = "";

        Object[] systemPropertyKeys = System.getProperties().keySet().toArray();
        for(int i = 0; i < systemPropertyKeys.length; i++) {
            String key = systemPropertyKeys[i].toString();

            stringBuilder += key;
            stringBuilder += " = ";
            stringBuilder += System.getProperty(key);
            stringBuilder += "<br>";
        }
        return new Response(stringBuilder);
    }

    public boolean isMatch(String path) {
        return path.equals("/bdj/variables");
    }
}
