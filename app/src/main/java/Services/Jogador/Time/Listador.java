package Services.Jogador.Time;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONObject;

import Objetos.Jogador;
import Objetos.Jogo;
import Objetos.Time;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Listador {

    public static Promise execute(Context context, Jogador jogador, Time time) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();

        HttpService.getInstance().get(
                context,
                "/api/players/" + jogador.getId() + "/teams/" + time.getId(),
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        deferred.resolve(response);
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
                        deferred.reject(throwable);
                    }
                });

        return promise;
    }
}
