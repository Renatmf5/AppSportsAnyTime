package Services.Endereco;

import android.app.Activity;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Endereco;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Encontrador {

    public static Promise execute(Activity activity, Endereco endereco) {
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();

        HttpService.getInstance().post(activity, "/api/addresses/" + endereco.getId(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                deferred.resolve(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                try {
                    JSONObject json = (JSONObject) timeline.get(0);
                    deferred.resolve(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                deferred.reject("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                deferred.reject("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                deferred.reject("");
            }
        });

        return promise;
    }
}
