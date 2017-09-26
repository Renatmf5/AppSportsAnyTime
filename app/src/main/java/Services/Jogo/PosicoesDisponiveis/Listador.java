package Services.Jogo.PosicoesDisponiveis;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Jogo;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Listador {
    public static Promise execute(Context context, Jogo jogo) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();

        HttpService.getInstance().get(
                context,
                "/api/games/" + jogo.getId() + "/available-positions",
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        deferred.resolve(response);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        deferred.resolve(response);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            deferred.resolve(new JSONObject(responseString));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        deferred.reject(errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        deferred.reject(errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        deferred.reject(throwable.getMessage());
                    }
                });

        return promise;
    }
}
