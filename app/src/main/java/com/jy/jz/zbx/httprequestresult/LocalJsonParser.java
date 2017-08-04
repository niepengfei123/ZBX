package com.jy.jz.zbx.httprequestresult;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json解析类
 */
public class LocalJsonParser {

    /**
     * converts a list into json string.
     * 
     * @param alJson
     * @return
     */
    public static <T> String list2Json(List<T> alJson) {
        if (alJson == null) {
            return null;
        }
        Type type = new TypeToken<List<T>>() {
        }.getType();
        Gson gson = new Gson();
        String strJson = gson.toJson(alJson, type);
        return strJson;
    }

    /**
     * converts a java bean into json string.
     * 
     * @param t
     * @return
     */
    public static <T> String bean2Json(T t) {
        if (t == null) {
            return null;
        }
        Type type = new TypeToken<T>() { }.getType();
        Gson gson = new Gson();
        String strJson = gson.toJson(t, type);
        return strJson;
    }

    public static <T> T json2Bean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(clazz, new TypeToken<T>() {}.getType());
        T dataset = gson.fromJson(json, objectType);
        return dataset;
    }

    /**
     * 把json转成HttpResult。
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> HttpResult<T> json2HttpResult(String json, Class<T> clazz) {
        JSONObject jsonObject = null;
        if (json != null) {
            try {
                jsonObject = new JSONObject(json);
                boolean hasData = jsonObject.has("data");
                if (hasData) {
                    Gson gson = new Gson();
                    Type objectType = type(HttpResult.class, clazz);
                    HttpResult<T> httpResult = gson.fromJson(json, objectType);
                    return httpResult;
                } else {
                    HttpResult<T> httpResult = new HttpResult<T>();
                    httpResult.setSucc(jsonObject.optBoolean("succ"));
                    httpResult.setCode(jsonObject.optInt("code"));
                    httpResult.setMsg(jsonObject.optString("msg"));
                    return httpResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }

    }
    
    /**
     * 把json转成HttpListResult。
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> HttpListResult<T> json2HttpListResult2(String json, Class<T> clazz) {
        JSONObject jsonObject = null;
        if (json != null) {
            try {
                jsonObject = new JSONObject(json);
                boolean hasData = jsonObject.has("data");
                if (hasData) {
                    JSONArray itemsJson = jsonObject.getJSONArray("data");
                    List<T> lData = new ArrayList<T>();
                    for (int i = 0; i < itemsJson.length(); i++) {
                        JSONObject temp = itemsJson.optJSONObject(i);
                        lData.add(json2Bean(temp.toString(), clazz));
                    }

                    HttpListResult<T> httpListResult = new HttpListResult<T>();
                    httpListResult.setSucc(jsonObject.optBoolean("succ"));
                    httpListResult.setCode(jsonObject.optInt("code"));
                    httpListResult.setMsg(jsonObject.optString("msg"));
                    httpListResult.setData(lData);
                    return httpListResult;
                } else {
                    HttpListResult<T> httpListResult = new HttpListResult<T>();
                    httpListResult.setSucc(jsonObject.optBoolean("succ"));
                    httpListResult.setCode(jsonObject.optInt("code"));
                    httpListResult.setMsg(jsonObject.optString("msg"));
                    return httpListResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }

    }

    public static <T> ParameterizedType type(final Class<T> raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }
            public Type[] getActualTypeArguments() {
                return args;
            }
            public Type getOwnerType() {
                return null;
            }
        };
    }

}
