package com.project.apptcc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import Services.Endereco.GeoPoint;

import Objetos.Jogo;
import Services.Endereco.DescobrirLatLong;

public class DetalhesJogo extends FragmentActivity implements OnMapReadyCallback {
    Jogo jogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogo);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapEndereco);
        mapFragment.getMapAsync(this);

        this.jogo = (Jogo)getIntent().getSerializableExtra("Jogo");
        ((TextView)findViewById(R.id.textViewNameTime)).setText(jogo.getTime().getNome());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String strEndereco = this.jogo.getEndereco().getAddress()
                + ", "
                + this.jogo.getEndereco().getCity()
                + ", "
                + this.jogo.getEndereco().getState();
        GeoPoint geoPoint = DescobrirLatLong.descobrir(this, strEndereco);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                .title(this.jogo.getTime().getNome()));
    }
}
