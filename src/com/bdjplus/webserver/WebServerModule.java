package com.bdjplus.webserver;

import com.sony.bdjstack.system.BDJModule;

import java.util.ArrayList;

public class WebServerModule {
    public static void main(String[] args) {

        System.setProperty("os.name", "OS X");
        BDJModule.properties.put("WebServerServices", new ArrayList());

        try {
            Thread t1 = new Thread(new WebServerThread(1111));
            t1.start(); // start thread
        }
        catch (Throwable e)
        {
            //BDJModule.log(e);
        }
    }

    public static void ready()
    {

    }
}