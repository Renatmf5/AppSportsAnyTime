package com.project.apptcc;

import android.app.ListActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import Objetos.Jogador;

public class HomeTime extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<Jogador> listItems=new ArrayList<Jogador>();
    ArrayAdapter<Jogador> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_time);

        this.initJogadoresList();
        this.initMap();
    }

    private void initJogadoresList() {
        ListView listView = (ListView) findViewById(R.id.listJogadores);
        adapter = new ArrayAdapter<Jogador>(
                this,
                android.R.layout.simple_list_item_2,
                listItems
        );
        listView.setAdapter(adapter);

        /**
         * - Chamar a API
         * - Carregar a lista de jogadores proximos
         * - Loop nos jogadores
         * - Adicionar à lista
         *
         * Jogador j = new Jogador();
         * listItems.add(j);
         *
         **/
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
