package ru.rgups.time.rest;

import android.util.Log;

import java.util.Map;

/**
 * Created by artoymtkachenko on 21.03.15.
 */
public class UrlBuilder {

    public static String make(String method, Map<String, String> params){
        StringBuffer result = new StringBuffer();
        result.append(UrlConstants.ENDPOINT)
              .append(method);
        if (params != null && !params.isEmpty()) {
            result.append("?");
            for (Map.Entry entry: params.entrySet()) {
                result.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        Log.e("UrlBuilder", "url :"+result);
        return result.toString();
    }

}
