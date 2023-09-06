package com.bdjplus.webserver.service;

import com.bdjplus.webserver.request.Request;
import com.bdjplus.webserver.response.Response;
import com.bdjplus.webserver.util.StringUtil;

import java.util.Iterator;
import java.util.Vector;

public class ClassService implements IService {

    private static Iterator list(ClassLoader CL)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Class CL_class = CL.getClass();
        while (CL_class != java.lang.ClassLoader.class) {
            CL_class = CL_class.getSuperclass();
        }
        java.lang.reflect.Field ClassLoader_classes_field = CL_class
                .getDeclaredField("classes");
        ClassLoader_classes_field.setAccessible(true);
        Vector classes = (Vector) ClassLoader_classes_field.get(CL);
        return classes.iterator();
    }

    public Response handle(Request request) throws Exception {
        String className = StringUtil.replace(request.path, "/bdj/class/", "");
        if(className.equals(""))
        {
            String classes = "";
            Iterator iterator = list(this.getClass().getClassLoader());
            while (iterator.hasNext()) {
                classes += iterator.next().toString();
                classes += "<br>";
            }

            return new Response(classes);
        }

        Class clazz = null;

        try {
            clazz = Class.forName(className);
        } catch (Exception e) {

        }

        if(clazz == null)
            return new Response("Class not found");
        else
            return new Response(clazz.toString());
    }

    public boolean isMatch(String path) {
        return path.startsWith("/bdj/class/");
    }
}
