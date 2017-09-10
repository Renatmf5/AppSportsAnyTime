package Services.UsuarioService;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Usuario;
import Services.HttpService;
import cz.msebera.android.httpclient.Header;

public class Criador {

    public static Promise execute(Usuario usuario) {
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();

        HttpService.post("/users", Criador.makeParams(usuario), new JsonHttpResponseHandler() {
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

    private static RequestParams makeParams(Usuario usuario) {
        RequestParams params = new RequestParams();
        params.add("name", usuario.getName());
        params.add("email", usuario.getEmail());
        params.add("password", usuario.getPassword());

        return params;
    }

}
