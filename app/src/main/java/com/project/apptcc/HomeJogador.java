package com.project.apptcc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jdeferred.DoneCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Time;
import Services.Time.Listador;

public class HomeJogador extends AppCompatActivity implements OnMapReadyCallback {
    Jogador jogador;
    ArrayList<Time> listItems=new ArrayList<Time>();
    ArrayAdapter<Time> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_jogador);

        this.jogador = (Jogador)getIntent().getSerializableExtra("Jogador");

        this.initTimesList();
        this.initMap();
    }

    private void initTimesList() {
        ListView listView = (ListView)findViewById(R.id.listJogadores);
        adapter = new ArrayAdapter<Time>(
                this,
                android.R.layout.simple_list_item_2,
                listItems
        );
        listView.setAdapter(adapter);

        Listador.execute().done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONArray data = ((JSONObject)result).getJSONArray("data");
                    for (int i = 0; i <= data.length(); i++) {
                        listItems.add(
                                new Time(data.getJSONObject(i))
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng fiap = new LatLng(-23.57681, -46.6311684);
        googleMap.addMarker(new MarkerOptions().position(fiap)
                .title("FIAP"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(fiap));
    }

}
