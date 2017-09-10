package Services.JogadorService;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import Objetos.Jogador;
import Services.HttpService;

public class Criador {

    public static Promise execute (Jogador jogador) {
        final Deferred deferred = new DeferredObject();
        Promise promise = deferred.promise();

        RequestParams params = new RequestParams();
        params.add("name", jogador.getNome());
        params.add("pitch_type", jogador.getTipoDeJogo());
        params.add("position", jogador.getPosicao());
        params.add("phone", jogador.getTelefone());
        params.add("user_id", jogador.getUsuario().getId());
        params.add("address_id", jogador.getEndereco().getId());

        HttpService.post("/players", params, new JsonHttpResponseHandler() {
            
        });

        return promise;
    }

}
