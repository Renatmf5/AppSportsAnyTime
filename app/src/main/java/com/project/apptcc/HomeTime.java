package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Time;
import Services.Jogador.Listador;

public class HomeTime extends AppCompatActivity implements OnMapReadyCallback {
    HomeTime context = this;
    Time time;
    ListView listView;
    ArrayList<Jogador> listItems = new ArrayList<Jogador>();
    ArrayAdapter<Jogador> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_time);

        this.time = (Time)getIntent().getSerializableExtra("Time");

        this.initJogadoresList();
        /*this.initMap();*/
    }

    private void initJogadoresList() {
        this.listView = (ListView) findViewById(R.id.listJogadores);
        this.adapter = new ArrayAdapter<Jogador>(
                this,
                android.R.layout.simple_list_item_1,
                this.listItems
        );
        this.listView.setAdapter(this.adapter);

        Listador.execute(this).done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONArray data = ((JSONArray)result);
                    for (int i = 0; i < data.length(); i++) {
                        Jogador jogador = new Jogador(data.getJSONObject(i));
                        context.listItems.add(jogador);
                    }
                    context.adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).fail(new FailCallback() {
            @Override
            public void onFail(Object result) {
                Toast.makeText(
                        context,
                        "Ocorreu um erro ao recuperar os jogadores. Por favor, tente novamente",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id
            ) {
                Jogador jogador = (Jogador) parent.getItemAtPosition(position);
                Intent i = new Intent(HomeTime.this, DetalhesJogador.class);
                i.putExtra("Jogador", jogador);
                startActivity(i);
            }
        });
    }

    private void initMap() {
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*LatLng fiap = new LatLng(-23.57681, -46.6311684);
        googleMap.addMarker(new MarkerOptions().position(fiap)
                .title("FIAP"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(fiap));*/
    }

    public void abrirNovoJogo(View view) {
        Intent i = new Intent(HomeTime.this, CadastroJogo.class);
        i.putExtra("Time", this.time);
        startActivity(i);
    }
}
