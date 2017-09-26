package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Jogo;
import Services.Endereco.DescobrirLatLong;
import Services.Endereco.GeoPoint;
import Services.Jogo.Listador;

public class HomeJogador extends AppCompatActivity implements OnMapReadyCallback {
    Jogador jogador;
    final HomeJogador context = this;
    ArrayList<Jogo> listItems=new ArrayList<Jogo>();
    ArrayAdapter<Jogo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_jogador);

        this.jogador = (Jogador)getIntent().getSerializableExtra("Jogador");
        Log.d(this.getLocalClassName(), this.jogador.getId());

        //this.initJogosList();
        this.initMap();
    }

    private void initJogosList(GoogleMap googleMap) {
        ListView listView = (ListView)findViewById(R.id.listTimes);
        context.adapter = new ArrayAdapter<Jogo>(
                this,
                android.R.layout.simple_list_item_1,
                context.listItems
        );
        listView.setAdapter(context.adapter);

        Listador.execute(context)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        try {
                            JSONArray data = (JSONArray) result;
                            for (int i = 0; i < data.length(); i++) {
                                Jogo jogo = new Jogo(data.getJSONObject(i));
                                context.listItems.add(jogo);

                                GeoPoint point = DescobrirLatLong.descobrir(
                                        context,
                                        jogo.getEndereco().getAddress()
                                        + ", "
                                        + jogo.getEndereco().getCity()
                                        + ", "
                                        + jogo.getEndereco().getState()
                                );
                                /*googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(point.getLatitude(), point.getLongitude()))
                                        .title(jogo.getTime().getNome()));*/
                            }
                            context.adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .fail(new FailCallback() {
                    @Override
                    public void onFail(Object result) {
                        Toast.makeText(
                                context,
                                "Ocorreu um erro ao recuperar os jogos. Por favor, tente novamente",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id
            ) {
                Jogo jogo = (Jogo)parent.getItemAtPosition(position);
                Intent i = new Intent(HomeJogador.this, DetalhesJogo.class);
                i.putExtra("Jogo", jogo);
                startActivity(i);
            }
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapJogos);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.initJogosList(googleMap);
    }
}
