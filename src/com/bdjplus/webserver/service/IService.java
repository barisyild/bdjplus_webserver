package com.bdjplus.webserver.service;

import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.response.Response;

public interface IService {
    Response handle(Request request) throws Exception;
    boolean isMatch(String path);
}
