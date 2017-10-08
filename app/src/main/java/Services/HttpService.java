package Services;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class HttpService {
    private String token;
    private static HttpService instance;
    private static final String BASE_URL = "http://ec2-52-14-152-222.us-east-2.compute.amazonaws.com";
    private static AsyncHttpClient client;

    protected HttpService(){
        this.client = new AsyncHttpClient();
        //this.client.setURLEncodingEnabled(false);
    }

    public static HttpService getInstance() {
        if (null == instance) {
            instance = new HttpService();
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Header[] headers = {new BasicHeader("Authorization", "Bearer " + token)};

        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Header[] headers = {new BasicHeader("Authorization", "Bearer " + token)};

        client.post(context, getAbsoluteUrl(url), headers, params, "application/x-www-form-urlencoded", responseHandler);
    }

    public void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
