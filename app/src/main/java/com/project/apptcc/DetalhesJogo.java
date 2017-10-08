package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.PosicaoDisponivelJogo;
import Services.Endereco.GeoPoint;

import Objetos.Jogo;
import Services.Endereco.DescobrirLatLong;
import Services.Jogo.PosicoesDisponiveis.Listador;

public class DetalhesJogo extends FragmentActivity implements OnMapReadyCallback {
    DetalhesJogo context = this;
    Jogo jogo;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogo);

        /*MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapEndereco);
        mapFragment.getMapAsync(this);*/

        this.jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
        this.jogo = (Jogo) getIntent().getSerializableExtra("Jogo");
        ((TextView)findViewById(R.id.textViewNameTime)).setText(jogo.getTime().getNome());

        this.preencherPosicoes();
    }

    private void preencherPosicoes() {
        Listador.execute(this, this.jogo)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        JSONArray json = (JSONArray)result;
                        String posStr = "";

                        for (int i = 0; i < json.length(); i++) {
                            try {
                                PosicaoDisponivelJogo posicao = new PosicaoDisponivelJogo(
                                        jogo,
                                        json.getJSONObject(i).getString("position"));
                                if (posStr.equals("")) {
                                    posStr = posicao.getPosicaoValueStr();
                                } else {
                                    posStr += ", " + posicao.getPosicaoValueStr();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ((TextView)findViewById(R.id.textViewPositionTime)).setText("Precisam de " + posStr);
                    }
                })
                .fail(new FailCallback() {
                    @Override
                    public void onFail(Object result) {
                        Toast.makeText(context, "Ocorreu um erro ao carregar as posições disponíveis " +
                                "para este jogo. Por favor, tente novamente", Toast.LENGTH_SHORT);
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*String strEndereco = this.jogo.getEndereco().getAddress()
                + ", "
                + this.jogo.getEndereco().getCity()
                + ", "
                + this.jogo.getEndereco().getState();
        GeoPoint geoPoint = DescobrirLatLong.descobrir(this, strEndereco);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                .title(this.jogo.getTime().getNome()));*/
    }

    public void queroJogar(View view) {
        Services.Jogo.Jogador.Criador.execute(DetalhesJogo.this, this.jogo, this.jogador)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        Intent i = new Intent(DetalhesJogo.this, Confirmacao.class);
                        i.putExtra("flag", Confirmacao.CONFIRMACAO_QUERO_JOGAR);
                        i.putExtra("Jogo", DetalhesJogo.this.jogo);
                        i.putExtra("Jogador", DetalhesJogo.this.jogador);
                        startActivity(i);
                    }
                })
                .fail(new FailCallback() {
                    @Override
                    public void onFail(Object result) {
                        Toast.makeText(DetalhesJogo.this, "Ocorreu um erro ao registrar o seu " +
                                "pedido para jogar. Por favor, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void avaliar(View view) {
        Intent i = new Intent(DetalhesJogo.this, Review.class);
        i.putExtra("flag", Confirmacao.CONFIRMACAO_QUERO_JOGAR);
        i.putExtra("Jogo", this.jogo);
        i.putExtra("TimeAvaliado", this.jogo.getTime());
        i.putExtra("JogadorAvaliador", this.jogador);
        startActivity(i);
    }
}
