package Services.Jogador;

import android.app.Activity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Jogador;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Criador {

    public static Promise execute (Activity activity, Jogador jogador) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.add("name", jogador.getNome());
        params.add("pitch_type", jogador.getTipoDeJogo());
        params.add("position", jogador.getPosicao());
        params.add("phone", jogador.getTelefone());
        params.add("user_id", jogador.getUsuario().getId());
        params.add("address_id", jogador.getEndereco().getId());

        HttpService.getInstance().post(activity, "/api/players", params, new JsonHttpResponseHandler() {
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
