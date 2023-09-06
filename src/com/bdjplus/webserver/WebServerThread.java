package com.bdjplus.webserver;

import com.bdjplus.webserver.parser.RequestParser;
import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.response.Response;
import com.bdjplus.webserver.service.*;
import com.bdjplus.webserver.util.StringUtil;
import com.sony.bdjstack.system.BDJModule;
import com.bdjplus.webserver.WebServerModule;

import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServerThread implements Runnable {
    private final int port;
    public WebServerThread(int port) {
        this.port = port;
    }

    public static List services;

    public void run() {
        services = (List) BDJModule.properties.get("WebServerServices");
        //
        try {
            services.add(VariablesService.class);
            services.add(FileService.class);
            services.add(CommandService.class);
            services.add(JarLoaderService.class);
            services.add(ClassService.class);
            services.add(LogService.class);
            services.add(ExitService.class);
            services.add(CreditsService.class);

            // Restart server if it was exception occured
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                //keep listens indefinitely until receives 'exit' call or program terminates
                while (true) {
                    // on reste bloqué sur l'attente d'une demande client
                    Socket clientSocket = serverSocket.accept();
                    System.err.println("Nouveau client connecté");

                    // on ouvre un flux de converation

                    InputStream inputStream = clientSocket.getInputStream();
                    OutputStream outputStream = clientSocket.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                    // chaque fois qu'une donnée est lue sur le réseau on la renvoi sur
                    // le flux d'écriture.
                    // la donnée lue est donc retournée exactement au même client.

                    Response response = null;
                    Request request = RequestParser.parse(inputStream);

                    try
                    {
                        if(request == null)
                        {
                            throw new Exception("Request is null");
                        }

                        IService service = findService(request.path);
                        if(service == null)
                        {
                            File file;
                            if(System.getProperty("os.version").equals("ORBIS"))
                            {
                                file = new File("/app0/cdc/modules/www/" + request.path);
                            }else{
                                // Windows
                                file = new File("modules/www/" + request.path);
                            }
                            if(file.exists())
                            {
                                byte[] bytes = new byte[(int) file.length()];
                                FileInputStream fis = new FileInputStream(file);
                                fis.read(bytes);
                                response = new Response(bytes);
                                response.setContentType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
                            }
                            else
                            {
                                response = new Response("404 Not Found");
                            }
                        }else{
                            response = service.handle(request);
                        }

                        bufferedWriter.write("HTTP/1.0 200 OK\r\n");
                    }
                    catch (Throwable e)
                    {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        String stackTrace = stringWriter.toString();
                        String responseString = "<title>Internal Server Error</title>" + stackTrace;
                        responseString = StringUtil.replace(responseString, "\n", "<br>");
                        response = new Response(responseString);

                        bufferedWriter.write("HTTP/1.0 500 Internal Server Error\r\n");
                    }

                    Object[] headerKeys = response.headers.keySet().toArray();
                    for (int i = 0; i < headerKeys.length; i++)
                    {
                        String headerKey = headerKeys[i].toString();
                        String headerValue = response.headers.get(headerKey).toString();
                        if(headerValue == null)
                            continue;
                        bufferedWriter.write(headerKey + ": " + headerValue + "\r\n");
                    }
                    bufferedWriter.write("Server: BDJ-Plus\r\n");
                    bufferedWriter.write("Content-Length: " + response.contentLength + "\r\n");
                    bufferedWriter.write("\r\n");
                    bufferedWriter.flush();
                    outputStream.write(response.bodyBytes);

                    // on ferme les flux.
                    System.err.println("Connexion avec le client terminée");
                    outputStream.close();
                    inputStream.close();
                    clientSocket.close();
                }
            }
            catch (Exception e)
            {
                BDJModule.log(e);
            }
        }
        catch (Exception e)
        {
            BDJModule.log(e);
        }
    }

    private IService findService(String path)
    {
        try {
            for (int i = 0; i < services.size(); i++)
            {
                Class serviceClass = (Class) services.get(i);
                IService service = (IService) serviceClass.newInstance();
                if(service.isMatch(path))
                {
                    return service;
                }
            }
        }
        catch (Exception e)
        {
            BDJModule.log(e);
        }
        return null;
    }
}
