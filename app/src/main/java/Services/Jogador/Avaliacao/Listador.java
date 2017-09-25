package Services.Jogador.Avaliacao;

import android.content.Context;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONObject;

import Objetos.Jogador;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Listador {

    public static Promise execute(Context context, Jogador jogador) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();
        String endpoint = "/api/players/" + jogador.getId() + "/reviews";

        HttpService.getInstance().get(context, endpoint, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
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
