package Services.Time.Avaliacao;

import android.app.Activity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Review;
import Objetos.Time;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Criador {

    public static Promise execute (Activity activity, Time time, Review review) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.put("grade", review.getGrade());
        params.add("description", review.getDescription());

        HttpService.getInstance().post(activity, "/api/teams/"+time.getId()+"/reviews", params, new JsonHttpResponseHandler() {
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
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject json = new JSONObject(responseString);
                    deferred.resolve(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                deferred.reject(null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                deferred.reject(null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                deferred.reject(null);
            }
        });

        return promise;
    }
}
