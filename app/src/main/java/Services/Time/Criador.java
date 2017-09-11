package Services.Time;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import Objetos.Time;
import Services.HttpService;

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

        HttpService.post("/teams", params, new JsonHttpResponseHandler() {

        });

        return promise;
    }

}
