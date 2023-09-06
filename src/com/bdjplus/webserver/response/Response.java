package com.bdjplus.webserver.response;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public int contentLength;
    public Map headers = new HashMap();
    public byte[] bodyBytes;
    // Optimization

    public Response(String response) {
        this.bodyBytes = response.getBytes();
        this.contentLength = this.bodyBytes.length;
        init();
    }

    public Response(byte[] response) {
        this.bodyBytes = response;
        this.contentLength = this.bodyBytes.length;
        init();
    }

    public void setContentType(String fileExtension)
    {
        if (fileExtension.equals("html"))
        {
            headers.put("Content-Type", "text/html");
        } else if (fileExtension.equals("css"))
        {
            headers.put("Content-Type", "text/css");
        } else if (fileExtension.equals("js"))
        {
            headers.put("Content-Type", "text/javascript");
        } else if (fileExtension.equals("png"))
        {
            headers.put("Content-Type", "image/png");
        } else if (fileExtension.equals("jpg"))
        {
            headers.put("Content-Type", "image/jpeg");
        } else if (fileExtension.equals("jpeg"))
        {
            headers.put("Content-Type", "image/jpeg");
        } else if (fileExtension.equals("gif"))
        {
            headers.put("Content-Type", "image/gif");
        } else if (fileExtension.equals("svg"))
        {
            headers.put("Content-Type", "image/svg+xml");
        } else if (fileExtension.equals("ico"))
        {
            headers.put("Content-Type", "image/x-icon");
        } else if (fileExtension.equals("json"))
        {
            headers.put("Content-Type", "application/json");
        } else if (fileExtension.equals("xml"))
        {
            headers.put("Content-Type", "application/xml");
        } else if (fileExtension.equals("pdf"))
        {
            headers.put("Content-Type", "application/pdf");
        } else if (fileExtension.equals("zip"))
        {
            headers.put("Content-Type", "application/zip");
        } else if (fileExtension.equals("gz"))
        {
            headers.put("Content-Type", "application/gzip");
        } else if (fileExtension.equals("tar"))
        {
            headers.put("Content-Type", "application/x-tar");
        } else if (fileExtension.equals("mp3"))
        {
            headers.put("Content-Type", "audio/mpeg");
        } else if (fileExtension.equals("wav"))
        {
            headers.put("Content-Type", "audio/wav");
        } else if (fileExtension.equals("ogg"))
        {
            headers.put("Content-Type", "audio/ogg");
        } else if (fileExtension.equals("mp4"))
        {
            headers.put("Content-Type", "video/mp4");
        } else if (fileExtension.equals("webm"))
        {
            headers.put("Content-Type", "video/webm");
        } else if (fileExtension.equals("flv"))
        {
            headers.put("Content-Type", "video/x-flv");
        } else if (fileExtension.equals("avi"))
        {
            headers.put("Content-Type", "video/x-msvideo");
        } else if (fileExtension.equals("wmv"))
        {
            headers.put("Content-Type", "video/x-ms-wmv");
        } else if (fileExtension.equals("mpeg"))
        {
            headers.put("Content-Type", "video/mpeg");
        } else {
            headers.put("Content-Type", "application/octet-stream");
        }
    }

    private void init()
    {
        headers.put("Content-Type", "text/html");
    }
}
