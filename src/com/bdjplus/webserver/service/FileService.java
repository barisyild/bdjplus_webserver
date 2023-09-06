package com.bdjplus.webserver.service;

import com.bdjplus.webserver.response.Response;
import com.sony.bdjstack.system.BDJModule;
import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;

public class FileService implements IService {

    public Response handle(Request request) throws Exception {
        String path = request.path;
        String filePath = StringUtil.replace(path, "/bdj/files", "");
        filePath = URLDecoder.decode(filePath);

        File file = new File(filePath);
        if(file.isFile())
        {
            try {
                byte[] bytes = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(bytes);
                Response response = new Response(bytes);
                response.headers.put("Content-Type", "application/octet-stream");
                return response;
            } catch (Exception e) {
                BDJModule.log(e);
            }
        }else{
            String stringBuilder = "";
            for (int i = 0; i < file.listFiles().length; i++) {
                stringBuilder += file.listFiles()[i].getName();
                stringBuilder += "<br>";
            }

            return new Response(stringBuilder.toString());
        }

        return new Response("404 Not Found");
    }

    public boolean isMatch(String path) {
        return path.startsWith("/bdj/files");
    }
}
