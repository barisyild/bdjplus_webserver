package com.bdjplus.webserver.parser;

import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.exception.NotSupportedRequestMethodException;
import com.bdjplus.webserver.util.StringUtil;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    public static Request parse(InputStream inputStream) throws NotSupportedRequestMethodException, IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        String rawRequest = "";

        byte[] contentByteMark = new byte[] { 0x0D, 0x0A, 0x0D, 0x0A };
        byte contentByteMarkIndex = 0;

        boolean isRequestCompleted = false;
        long requestTimeout = System.currentTimeMillis() + 10000;
        while (requestTimeout > System.currentTimeMillis() && !isRequestCompleted)
        {
            while (bufferedInputStream.available() > 0) {
                char c =  (char) bufferedInputStream.read();

                if(c == contentByteMark[contentByteMarkIndex])
                {
                    contentByteMarkIndex++;
                }else if(c == contentByteMark[0]) {
                    contentByteMarkIndex = 1;
                }else {
                    contentByteMarkIndex = 0;
                }

                if(contentByteMarkIndex == contentByteMark.length)
                {
                    isRequestCompleted = true;
                    break;
                }

                rawRequest += c;
            }
        }

        if(rawRequest.equals("")) {
            return null;
        }

        String[] requestParts = StringUtil.split(rawRequest, "\r\n");
        String requestMethodRaw = StringUtil.split(requestParts[0], " ")[0].toUpperCase();

        String requestMethod = getRequestMethod(requestMethodRaw);
        if(requestMethod == null) {
            throw new NotSupportedRequestMethodException(requestMethodRaw);
        }

        String requestPath = StringUtil.split(requestParts[0], " ")[1];
        requestPath = URLDecoder.decode(requestPath);
        requestPath = StringUtil.replace(requestPath, "..", "");
        requestPath = StringUtil.replace(requestPath, "./", "");
        requestPath = StringUtil.replace(requestPath, ".\\", "");
        requestPath = StringUtil.replace(requestPath, "\\", "/");

        if(!requestPath.startsWith("/"))
            requestPath = "/" + requestPath;

        if(requestPath.equals("/"))
            requestPath = "/index.html";

        if(requestPath.indexOf("?") != -1)
            requestPath = requestPath.substring(0, requestPath.indexOf("?"));

        Map headers = new HashMap();
        for(int i = 1; i < requestParts.length; i++) {
            String[] headerParts = StringUtil.split(requestParts[i], ": ");
            if(headerParts.length == 2)
                headers.put(headerParts[0], headerParts[1]);
        }

        int contentLength = 0;
        if(headers.get("Content-Length") != null) {
            contentLength = Integer.parseInt(headers.get("Content-Length").toString());
            if(contentLength < 0)
                contentLength = 0; // Prevent negative content length
        }

        int bodyStep = 0;
        byte[] bodyBytes = new byte[contentLength];
        while (requestTimeout > System.currentTimeMillis() && bodyStep != contentLength)
        {
            while (bufferedInputStream.available() > 0) {
                bodyBytes[bodyStep] =  (byte) bufferedInputStream.read();
                bodyStep++;
            }
        }

        Request request = new Request();
        request.method = requestMethod;
        request.path = requestPath;
        request.contentLength = contentLength;
        request.headers = headers;
        request.bodyBytes = bodyBytes;
        return request;
    }

    private static String getRequestMethod(String requestMethod) {
        if(requestMethod.equals("GET") || requestMethod.equals("POST"))
            return requestMethod;

        return null;
    }
}
