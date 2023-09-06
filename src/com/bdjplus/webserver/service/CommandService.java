package com.bdjplus.webserver.service;

import com.bdjplus.webserver.response.Response;
import com.sony.bdjstack.system.BDJModule;
import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

public class CommandService implements IService {
    public Response handle(Request request) throws Exception {
        String path = request.path;
        String command = StringUtil.replace(path, "/bdj/command/", "");
        command = URLDecoder.decode(command);

        String stringBuilder = "";
        try {
            String response = "";
            String line;
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = inputBufferedReader.readLine()) != null) {
                response += line + "<br>";
            }

            BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorBufferedReader.readLine()) != null) {
                response += line + "<br>";
            }
            stringBuilder += response;
        } catch (IOException e) {
            BDJModule.log(e);
        }

        return new Response(stringBuilder);
    }

    public boolean isMatch(String path) {
        return path.startsWith("/bdj/command");
    }
}
