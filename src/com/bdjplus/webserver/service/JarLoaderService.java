package com.bdjplus.webserver.service;

import com.sony.bdjstack.system.BDJModule;
import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.response.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JarLoaderService implements IService {

    public Response handle(Request request) throws Exception {
        if(request.method.equals("GET"))
        {
            return new Response("<input\n" +
                    "  type=\"file\"\n" +
                    "  id=\"file\"\n" +
                    "  accept=\".jar\" />\n" +
                    "<script>\n" +
                    "\tdocument.querySelector('input').addEventListener('change', function() {\n" +
                    "\n" +
                    "\tvar reader = new FileReader();\n" +
                    "\treader.onload = function() {\n" +
                    "\t\tvar arrayBuffer = this.result;\n" +
                    "\t\tlet http = new XMLHttpRequest();\n" +
                    "\t\thttp.open(\"POST\", \"/bdj/jar/loader\");\n" +
                    "\t\thttp.send(arrayBuffer);\n" +
                    "\t}\n" +
                    "\treader.readAsArrayBuffer(this.files[0]);\n" +
                    "\n" +
                    "}, false);\n" +
                    "</script>");
        }

        try {
            File file = File.createTempFile("jarLoader", ".jar");
            file.deleteOnExit();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(request.bodyBytes);
            fileOutputStream.close();

            BDJModule.loadJar(file.getPath(), true);
        } catch (IOException e) {
            BDJModule.log(e);
        }
        return new Response("OK");
    }

    public boolean isMatch(String path) {
        return path.equals("/bdj/jar/loader");
    }
}
