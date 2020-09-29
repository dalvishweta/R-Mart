package com.rmart.utilits.ccavenue;

import com.rmart.utilits.LoggerInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Satya Seshu on 27/09/20.
 */
public class ServiceUtility {

    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";

    public Map<Object, Object> readProperties(String pFilePath) {
        Map<Object, Object> vPropertyMap = null;
        Set<Object> vTckey;
        Iterator<Object> vTcPropItr;
        Properties vTCProperties;
        try {
            vTCProperties = new Properties();
            vTCProperties.load(ServiceUtility.class.getResourceAsStream(pFilePath));
            vTckey = vTCProperties.keySet();
            vTcPropItr = vTckey.iterator();
            vPropertyMap = new LinkedHashMap<>();
            while (vTcPropItr.hasNext()) {
                Object vKey = vTcPropItr.next();
                vPropertyMap.put(vKey, vTCProperties.get(vKey));
            }
        } catch (Exception ex) {
            LoggerInfo.errorLog("Exception", ex.getMessage());
        }
        return vPropertyMap;
    }

    public static Object chkNull(Object pData) {
        return (pData == null ? "" : pData);
    }

    public static Map<Object, Object> tokenizeToHashMap(String msg, String delimPairValue, String delimKeyPair) throws Exception {
        Map<Object, Object> keyPair = new HashMap<>();
        List<String> respList;
        String part = "";
        StringTokenizer strTkn = new StringTokenizer(msg, delimPairValue, true);
        while (strTkn.hasMoreTokens()) {
            part = (String) strTkn.nextElement();
            if (part.equals(delimPairValue)) {
                part = null;
            } else {
                respList = tokenizeToArrayList(part, delimKeyPair);
                if (respList.size() == 2) keyPair.put(respList.get(0), respList.get(1));
                else if (respList.size() == 1) keyPair.put(respList.get(0), null);
            }
            if (part == null) continue;
            if (strTkn.hasMoreTokens()) strTkn.nextElement();
        }
        return keyPair.size() > 0 ? keyPair : null;
    }

    public static List<String> tokenizeToArrayList(String msg, String delim) throws Exception {
        List<String> respList = new ArrayList<>();
        String varName;
        String varVal = null;
        int index = msg.indexOf(delim);
        varName = msg.substring(0, index);
        if ((index + 1) != msg.length())
            varVal = msg.substring(index + 1, msg.length());
        respList.add(varName);
        respList.add(varVal);
        return respList.size() > 0 ? respList : null;
    }

    public static String addToPostParams(String paramKey, String paramValue) {
        if (paramValue != null)
            return paramKey.concat(PARAMETER_EQUALS).concat(paramValue)
                    .concat(PARAMETER_SEP);
        return "";
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
