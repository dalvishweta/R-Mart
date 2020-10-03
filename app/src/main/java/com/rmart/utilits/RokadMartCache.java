package com.rmart.utilits;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Satya Seshu on 03/10/20.
 */
public class RokadMartCache {

    private RokadMartCache() {

    }

    private static final String CACHE_PREFERENCE = "application_cache";

    public static void putData(String key, Context pContext, Object dataToCache) {
        saveDataToDisk(key, pContext, dataToCache);
    }

    public static Object getData(String key, Context pContext) {
        Object lReturnData;
        lReturnData = null;
        SharedPreferences prefs = pContext.getSharedPreferences(CACHE_PREFERENCE, PreferenceActivity.MODE_PRIVATE);
        String data = prefs.getString(key, null);
        if(data != null){
            try {
                lReturnData = deserializeObjectFromString(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lReturnData;
    }

    @SuppressWarnings("deprecation")
    private static void saveDataToDisk(String key,Context pContext, Object dataToCache) {
        SharedPreferences prefs = pContext.getSharedPreferences(CACHE_PREFERENCE, PreferenceActivity.MODE_MULTI_PROCESS);
        SharedPreferences.Editor lEditor = prefs.edit();
        try {
            lEditor.putString(key, serializeObjectToString(dataToCache));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lEditor.apply();
    }

    private static String serializeObjectToString(Object object) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
        ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut);
        objectOut.writeObject(object);
        objectOut.close();
        byte[] bytes = baos.toByteArray();
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    private static Object deserializeObjectFromString(String objectString) throws Exception {
        byte[] bytes = Base64.decode(objectString, Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        GZIPInputStream gzipIn = new GZIPInputStream(bais);
        ObjectInputStream objectIn = new ObjectInputStream(gzipIn);
        Object lObject = objectIn.readObject();
        objectIn.close();

        return lObject;
    }
}
