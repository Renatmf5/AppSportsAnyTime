package Services.Login;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class LoginService {

    public static Promise execute (String username, String password) {
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", password);
        params.add("grant_type", "password");
        params.add("client_id", "2");
        params.add("client_secret", "CiXbtOGIau2WPyiAY40lyCCvsqkx2SrE2JxQJ1vl");
        params.add("scope", "*");

        HttpService.getInstance().post(
                "/oauth/token",
                params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        deferred.resolve(response);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        try {
                            JSONObject json = (JSONObject) response.get(0);
                            deferred.resolve(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        try {
                            deferred.reject(errorResponse.get(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        deferred.reject(errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        try {
                            deferred.reject(new JSONObject(responseString));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        return promise;
    }

    public static Promise authenticatedUser (Context context) {
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();

        HttpService.getInstance().get(context, "/api/authenticated-user", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                deferred.resolve(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                deferred.reject(throwable.getLocalizedMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                deferred.reject(throwable.getLocalizedMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                deferred.reject(throwable.getLocalizedMessage());
            }
        });

        return promise;
    }

}
