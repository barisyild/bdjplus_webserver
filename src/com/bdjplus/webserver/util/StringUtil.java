package com.bdjplus.webserver.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static String[] split(String source, String delimiter){
        if (source == null){
            return new String[0];
        }
        if (source.indexOf(delimiter) == -1){
            return new String[]{source};
        }
        int ix = 0;
        int ix2 = 0;
        int delSize = delimiter.length();
        List v = new ArrayList();
        while (( ix2 = source.indexOf(delimiter, ix)) >= 0){
            v.add(source.substring(ix, ix2));
            ix = ix2 + delSize;
        }
        if (ix < source.length()){
            v.add(source.substring(ix));
        }
        String[] ret = new String[v.size()];
        for(int i = 0; i < ret.length; i++){
            ret[i] = (String) v.get(i);
        }
        return ret;
    }

    public static String replace(String source, String toReplace, String replacement){
        if (source.indexOf(toReplace) > -1){
            StringBuffer sb = new StringBuffer();
            int ix = -1;
            while (( ix = source.indexOf(toReplace)) >= 0){
                sb.append(source.substring(0,ix)).append(replacement);
                source = source.substring(ix + toReplace.length());
            }
            if (source.length() > 1){
                sb.append(source);
            }
            return sb.toString();
        }
        else {
            return source;
        }
    }
}
