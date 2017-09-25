package Services.Jogo;

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

import java.text.SimpleDateFormat;

import Objetos.Jogo;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Criador {

    public static Promise execute (Context context, Jogo jogo) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.add("team_id", jogo.getTime().getId());
        params.add("dated_at", jogo.getDatadoEm().getYear() + "-" + jogo.getDatadoEm().getMonth() + "-" + jogo.getDatadoEm().getDay());
        params.add("address_id", jogo.getEndereco().getId());

        HttpService.getInstance().post(context, "/api/games", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                deferred.resolve(response);
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
