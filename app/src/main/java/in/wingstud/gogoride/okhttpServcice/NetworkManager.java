    package in.wingstud.gogoride.okhttpServcice;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.okhttpServcice.response.ResponseClass;
import in.wingstud.gogoride.util.Constants;
import in.wingstud.gogoride.util.SharedPref;
import in.wingstud.gogoride.util.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


    public class NetworkManager<T> {
        private static OkHttpClient client;
        private static MediaType JSON;
        private static Context context;

        private static RequestBody body;
        private static Request request;
        private static MultipartBody.Builder requestBody;
        public static final String MEDIA_TYPE_JPG = "image/jpg";
        public static final String MEDIA_TYPE_PNG = "image/png";

        private OnCallback<T> callback;
        private Class<T> dataClass;

        public NetworkManager(Class<T> dataClass, OnCallback<T> callBack){
            this.dataClass = dataClass;
            this.callback = callBack;
        }

        public interface OnCallback<T> {
            public void onSuccess(boolean success, ResponseClass<T> responseClass, int which);

            public void onFailure(boolean success, String response, int which);
        }

        public void callAPIJson(final Context context, String callType, String url, String params, String title, final boolean isShowLoader, final int which) throws UnsupportedEncodingException {

            if (!Utils.isDeviceOnline(context)){
                Toast.makeText(context, context.getString(R.string.no_internet_connection_available), Toast.LENGTH_LONG).show();
            } else {

                if (isShowLoader) {
//                    Utils.progressDialog(context, "loading");
                }

                if (params != null && params.length() > 0) {
                    body = RequestBody.create(JSON, params);
                }

                if (callType.equals(Constants.VAL_POST)) {
                    request = new Request.Builder()
                            .header(Constants.FLD_CONTENT_TYPE, Constants.VAL_CONTENT_TYPE)
//                            .header("Authorization", SharedPref.getBasicAuth(context))
                            .url(url)
                            .post(body)
                            .build();

                } else if (callType.equals(Constants.VAL_GET)) {
                    request = new Request.Builder()
                            .header(Constants.FLD_CONTENT_TYPE, Constants.VAL_CONTENT_TYPE)
//                            .header("Authorization", SharedPref.getBasicAuth(context))
                            .url(url)
                            .get()
                            .build();
                } else if (callType.equals(Constants.VAL_DELETE)) {
                    request = new Request.Builder()
                            .header(Constants.FLD_CONTENT_TYPE, Constants.VAL_CONTENT_TYPE)
//                            .header("Authorization", SharedPref.getBasicAuth(context))
                            .url(url)
                            .get()
                            .build();
                }

    //        OkHttpClient httpClient = new OkHttpClient();

                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS).build();

                httpClient.newCall(request).enqueue(new Callback() {
                    Handler mainHandler = new Handler(context.getMainLooper());

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Responce", " onFailure ");
                        if (isShowLoader) {
//                            Utils.dismissProgressDialog();
                        }
                        mainHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                callback.onFailure(false, "", which);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String res = response.body().string();

                        Log.e("Responce", " Success " + res);
    //                    callback.onSuccess(true, res, which);
                        if (isShowLoader) {
//                            Utils.dismissProgressDialog();
                        }

                        mainHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                successResponse(res, which);
                            }
                        });
                    }
                });
            }
        }




        private void successResponse(String s, int which) {

            ResponseClass response = new ResponseClass<T>(dataClass);
            ResponseClass responseClass = new Gson().fromJson(s, response);
            responseClass.setCompletePacket(s);
            successResponse(responseClass, which);
        }

        private void successResponse(ResponseClass<T> responseClass, int which) {
            if (callback != null)
                callback.onSuccess(true, responseClass, which);

        }
    }
