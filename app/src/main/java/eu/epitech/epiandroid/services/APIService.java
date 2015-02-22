package eu.epitech.epiandroid.services;

import android.util.Log;
import android.util.Xml;

import com.loopj.android.http.*;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;


public class APIService {

    private static String mToken = null;
    private static String BASE_URL;
    private static int TIME_OUT = 5 * 1000;

    public static String getBASE_URL() {
        return BASE_URL;
    }

    public static void setBASE_URL(String BASE_URL) {
        APIService.BASE_URL = BASE_URL;
    }

    public static int getTIME_OUT() {
        return TIME_OUT;
    }

    public static void setTIME_OUT(int TIME_OUT) {
        APIService.TIME_OUT = TIME_OUT;
    }

    public static AsyncHttpClient getmClient() {
        return mClient;
    }

    public static void setmClient(AsyncHttpClient mClient) {
        APIService.mClient = mClient;
    }

    private static AsyncHttpClient mClient = new AsyncHttpClient();

    public static void initialize(String urlApi) {
        BASE_URL = urlApi;
        mClient.setMaxRetriesAndTimeout(0, TIME_OUT);
    }


    public static void postRequest(String section, RequestParams requestParams, JsonHttpResponseHandler responseHandler) {

        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        requestParams.add("token", mToken);
        mClient.post(BASE_URL + section, requestParams, responseHandler);
    }

    public static void getRequest(String section, RequestParams requestParams, JsonHttpResponseHandler responseHandler) {

        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        mClient.get(BASE_URL + section, requestParams, responseHandler);
        }

}