package com.jy.jz.zbx.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okio.BufferedSink;

/**
 * Created by lijin on 2016/7/26.
 * okhttp封装类
 */
public class OkHttpUtils {
    //创建okHttpClient对象
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Gson mGson;
    private Handler mDelivery;
    private static OkHttpUtils mInstance;

    /**
     * 初始化
     */
    private OkHttpUtils(){
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mOkHttpClient.setConnectTimeout(20000,TimeUnit.SECONDS);
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    /**
     * 单例模式
     * @return
     */
    public static OkHttpUtils getInstance(){
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }


    /**
     * 开启异步线程访问网络
     * @param request
     * @param callback
     */
    public void enqueue(Request request,final RequestCallBack<T> callback){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try
                {
                    final String string = response.body().string();
                    if (callback.mType == String.class){
                        sendSuccessResultCallback(string, callback);
                    }else{
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }
                } catch (IOException e){
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e){//Json解析的错误
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }
        });
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     * @param request
     */
    public void enqueue(Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response arg0) throws IOException {
            }
            @Override
            public void onFailure(Request arg0, IOException arg1) {
            }
        });
    }

    public static void get(String url){
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //非阻塞式访问，请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                //String htmlStr =  response.body().string();
                //byte[] resultBytes = response.body().bytes();
                //InputStream inputStream = response.body().byteStream();
            }
        });
//        阻塞式访问
//        Response response = call.execute();
    }

    /**
     * 下载文件
     * @param url
     * @throws Exception
     */
    public void getForAsynchronization(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                System.out.println(response.body().string());
            }
        });
    }

    /**
     * 下载文件  --对外暴露的接口
     * @param url
     */
    public static void getFile(String url){
        getInstance().getForAsynchronization(url);
    }

    /**
     * 以post方式提交json数据
     * @param url
     * @param json
     * @param callback
     * @throws IOException
     */
    public void postForJsonAsynchronization(String url, String json,RequestCallBack callback){
        Request request = buildPostRequest(url,json);
        getInstance().enqueue(request,callback);
    }

    /**
     * Post方式提交表单数据
     * @param url
     * @param json
     * @param callback
     * @return
     * @throws IOException
     */
    public static void postForMapAsynchronization(String url, String json,RequestCallBack callback){
        RequestBody formBody = new FormEncodingBuilder()
                .add("param", json)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        getInstance().enqueue(request,callback);
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    /**
     * 以post方式提交string数据
     * @param url
     * @param str
     * @param callback
     * @throws Exception
     */
    public static void postStringForAsynchronization(String url, String str,RequestCallBack callback){
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, str))
                .build();
        getInstance().enqueue(request,callback);
    }

    /**
     * 以post方式提交Byte数据
     * @param url
     * @param str
     * @param callback
     * @throws Exception
     */
    public static void postByteForAsynchronization(String url, final String str, RequestCallBack callback){
        RequestBody requestBody = new RequestBody() {
            @Override public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }
            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(str);
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(str, i, factor(i)));
                }
            }
            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        getInstance().enqueue(request,callback);
    }

    /**
     * 上传文件
     * @param url
     * @param filePath
     * @param callback
     * @throws Exception
     */
    public static void postFileForAsynchronization(String url, final String filePath , RequestCallBack callback){
        File file = new File(filePath);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        getInstance().enqueue(request,callback);
    }


    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 分块请求
     * @param url
     * @param json
     * @param filePath
     * @param callback
     * @throws Exception
     */
    public void postMultipartForAsynchronization(String url, String json, final String filePath, RequestCallBack callback){
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null, json))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"image\""),
                        RequestBody.create(MEDIA_TYPE_PNG, new File(filePath)))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url(url)
                .post(requestBody)
                .build();
        getInstance().enqueue(request,callback);
    }

    /**
     * 回掉接口
     * @param <T>
     */
    public static  abstract class RequestCallBack<T> {
        Type mType;
        public RequestCallBack()
        {
            mType = getSuperclassTypeParameter(getClass());
        }
        static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    /**
     * 请求错误
     * @param request
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final RequestCallBack callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    /**
     * 请求成功
     * @param object
     * @param callback
     */
    private void sendSuccessResultCallback(final Object object, final RequestCallBack callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
            }
        });
    }
//    /**
//     * Post方式提交表单数据
//     *
//     * @param url
//     * @param map
//     * @param callback
//     * @return
//     * @throws IOException
//     */
//    public void postForMapAsynchronization2(String url, Map<String, Object> map, RequestCallBack callback) {
//       Request request = buildPostRequest(url, map);
//        getInstance().enqueue(request, callback);
//    }
//    /**
//     * 组装Request
//     *
//     * @param url
//     * @param object
//     * @return
//     */
//    private Request buildPostRequest2(String url, Object object) {
//        String token = "";
//        Param[] params = null;
//        if (object instanceof String) {
//            params = json2Params((String) object);
//        } else if (object instanceof Map) {
//            params = map2Params((Map<String, Object>) object);
//        }
//        if (params == null) {
//            params = new Param[0];
//        }
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        //设置类型
//        builder.setType(MultipartBody.FORM);
//        for (Param param : params) {
//            if (param.value != null) {
//                if (param.value instanceof File) {
//                    File file = (File) param.value;
//                    builder.addFormDataPart(param.key, file.getName(), okhttp3.RequestBody.create(MEDIA_TYPE_PNG, file));
//                } else if (param.value instanceof String) {
//                    if (param.key.equals("token")) {
//                        token = (String) param.value;
//                    }
//                    builder.addFormDataPart(param.key, (String) param.value);
//                } else if (param.value instanceof Integer) {
//                    builder.addFormDataPart(param.key, String.valueOf(param.value));
//                } else if (param.value instanceof Double) {
//                    builder.addFormDataPart(param.key, String.valueOf(param.value));
//                } else if (param.value instanceof Float) {
//                    builder.addFormDataPart(param.key, String.valueOf(param.value));
//                } else if (param.value instanceof Long) {
//                    builder.addFormDataPart(param.key, String.valueOf(param.value));
//                } else {
//                    builder.addFormDataPart(param.key, (String) param.value);
//                }
//            }
//        }
//        //创建RequestBody
//        okhttp3.RequestBody requestBody2 = builder.build();
//
//        return new Request.Builder()
//                .url(url)
//                .post(requestBody2)
//                .addHeader("token", token)
//                .build();
//    }
    /**
     * 组装Request
     * @param url
     * @param object
     * @return
     */
    private Request buildPostRequest(String url, Object object)
    {
        Param[] params = null;
        if(object instanceof String){
            params = json2Params((String) object);
        }else if(object instanceof Map){
            params = map2Params((Map<String, String>) object);
        }
        if (params == null)
        {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params)
        {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * param类
     */
    public static class Param
    {
        public Param()
        {
        }

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }


    private Param[] map2Params(Map<String, String> params)
    {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries)
        {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private Param[] json2Params(String params)
    {
        if (params == null) return new Param[0];
        Gson gson = new Gson();
        Map infoMap = gson.fromJson(params, new TypeToken<Map<String, String>>(){}.getType());
        return map2Params(infoMap);
    }

//
//    private Param[] map2Params(Map<String, Object> params) {
//        if (params == null) return new Param[0];
//        int size = params.size();
//        Param[] res = new Param[size];
//        Set<Map.Entry<String, Object>> entries = params.entrySet();
//        int i = 0;
//        for (Map.Entry<String, Object> entry : entries) {
//            res[i++] = new Param(entry.getKey(), entry.getValue());
//        }
//        return res;
//    }
//
//    private Param[] json2Params(String params) {
//        if (params == null) return new Param[0];
//        Gson gson = new Gson();
//        Map infoMap = gson.fromJson(params, new TypeToken<Map<String, Object>>() {
//        }.getType());
//        return map2Params(infoMap);
//    }
}
