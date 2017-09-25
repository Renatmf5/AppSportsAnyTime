package Services.Time;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONObject;

import Objetos.Time;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Criador {

    public static Promise execute (Time time) {
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.add("name", time.getNome());
        params.add("pitch_type", time.getTipoDeJogo());
        params.add("responsible_phone", time.getTelefone());
        params.add("user_id", time.getUsuario().getId());
        params.add("address_id", time.getEndereco().getId());

        HttpService.getInstance().post("/api/teams", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                deferred.resolve(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                deferred.reject(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                deferred.reject(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                deferred.reject(throwable.getMessage());
            }
        });

        return promise;
    }

}
