package pxm.com.pxm.source.utils;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dmtec on 2016-07-19.
 *  a util class that contains methods used to do network
 */
public class OkHttpUtil {
    private  OkHttpClient client;
    private Handler handler;
    private volatile static OkHttpUtil okHttpUtil;

    private  static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");

    private  OkHttpUtil(){
        client=new OkHttpClient();
        handler=new Handler(Looper.getMainLooper());
    }


    public static OkHttpUtil getInstance(){
        if(okHttpUtil==null){
            synchronized (OkHttpUtil.class){
                okHttpUtil=new OkHttpUtil();
            }
        }
        return okHttpUtil;
    }



    /**
     * upload a file to server
     * @param url url
     * @param filename file name
     * @param file File
     * @param listener callback listener
     */
    public void sendMultiPartAsync(String url,String filename, File file,final Func_jsonString listener){
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId",MyApplication.userId)
                .addFormDataPart("portrait",filename,RequestBody.create(MediaType.parse("image/png"),file))
                .build();

        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJsonString(response.body().string(),listener);
                }
                else{
                    listener.onError();
                }
            }
        });

    }


    /**
     * do http get synchronously
     * @param url url
     * @return response (json text)
     */
    public String doGetSync(String url){
        Request request=new Request.Builder().url(url).build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream doGetStreamSync(String url){
        Request request=new Request.Builder().url(url).build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().byteStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] doGetBytesSync(String url){
        Request request=new Request.Builder().url(url).build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if (response.isSuccessful()){
                return response.body().bytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doGetBytesAsync(String url, final Func_bytes callback){
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callback.onSuccess(response.body().bytes());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }

    /**
     * do http post synchronously
     * @param url url
     * @param jsonString json text that is to be post
     * @return response (json text)
     */
    public String doPostSync(String url,String jsonString){
        RequestBody body=RequestBody.create(JSON,jsonString);
        Request request=new Request.Builder().url(url).post(body).build();
        Response response=null;
        try {
            response=client.newCall(request).execute();
            if (response.isSuccessful()){
                return  response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get json text from server
     * @param url url of server
     * @param listener callback listener
     */
    public void doGetAsync(String url,final Func_jsonString listener){
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJsonString(response.body().string(),listener);
                }
                else {
                    listener.onError();
                }
            }
        });
    }

    /**
     * to post a json text to server
     * @param url server address
     * @param jsonString json String
     * @param listener callback listener
     */
    public void doPostAsync(String url, String jsonString, final Func_jsonString listener){
        Log.d("TAG","doPostAsync");
        RequestBody body=RequestBody.create(JSON,jsonString);
        Request request=new Request.Builder().url(url).post(body).build();
        Log.d("TAG","build");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG","fail");
                listener.onError();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccessJsonString(response.body().string(),listener);
            }
        });
    }

    /**
     * Called when succeed getting response from server
     * @param jsonString response
     * @param callback callback listener
     */
    private void onSuccessJsonString(final String jsonString, final Func_jsonString callback){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback!=null){
                    try{
                        callback.onSuccess(jsonString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }





    /**
     * Called when succeed getting response from server
     * @param bytes response
     * @param callback callback listener
     */
    private void onSuccessByteStream(final InputStream bytes, final Func_byteStream callback){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback!=null){
                    try{
                        callback.onSuccess(bytes);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    /**
     * Callback interface (when the data_response  type is json_text)
     */
    public interface Func_jsonString{
        /**
         * success to get json text from server
         * @param result json text
         */
        void onSuccess(String result);

        /**
         * fail to get from server
         */
        void onError();
    }

    /**
     * Callback interface (when the data_response  type is byte array)
     */
    public interface Func_byteStream {
        /**
         * success to get bytes from server
         * @param result bytes get from server
         */
        void onSuccess(InputStream result);

        /**
         * fail to get from server
         */
        void onError();
    }


    public interface Func_bytes {
        /**
         * success to get bytes from server
         * @param result bytes get from server
         */
        void onSuccess(byte[] result);

        /**
         * fail to get from server
         */
        void onError();
    }

}
