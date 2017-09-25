package Services.Jogo.PosicoesDisponiveis;

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

import Objetos.PosicaoDisponivelJogo;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Criador {
    public static Promise execute (Context context, PosicaoDisponivelJogo posicao) {
        final Deferred deferred = new DeferredObject();
        final Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.add("position", posicao.getPosicao());

        HttpService.getInstance().post(
                context,
                "/api/games/" + posicao.getJogo().getId() + "/available-positions",
                params,
                new JsonHttpResponseHandler() {
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
