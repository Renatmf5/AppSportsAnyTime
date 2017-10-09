package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class HomeJogador extends FragmentActivity implements OnMapReadyCallback {
    private final HomeJogador context = this;
    private ArrayList<String> posicoes = new ArrayList<>();
    private Jogador jogador;
    private ArrayList<Jogo> listItems=new ArrayList<Jogo>();
    private ArrayAdapter<Jogo> adapter;
    private GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_jogador);

        if (getIntent().hasExtra("posicoes")) {
            this.posicoes = getIntent().getStringArrayListExtra("posicoes");
        }
        this.jogador = (Jogador)getIntent().getSerializableExtra("Jogador");
        this.initJogosList();
    }

    private void initJogosList() {
        ListView listView = (ListView)findViewById(R.id.listTimes);
        context.adapter = new ArrayAdapter<Jogo>(
                this,
                android.R.layout.simple_list_item_1,
                context.listItems
        );
        listView.setAdapter(context.adapter);

        Listador.execute(context, this.posicoes)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        try {
                            JSONArray data = (JSONArray) result;
                            for (int i = 0; i < data.length(); i++) {
                                Jogo jogo = new Jogo(data.getJSONObject(i));
                                context.listItems.add(jogo);
                            }
                            context.adapter.notifyDataSetChanged();
                            context.initMap();
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
                i.putExtra("Jogador", jogador);
                startActivity(i);
            }
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapJogos);
        mapFragment.getMapAsync(this);
        //this.mapa = (MapFragment)getFragmentManager().findFragmentById(R.id.mapJogos);
        //this.mapa.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Jogo jogo: this.listItems) {
            GeoPoint point = DescobrirLatLong.descobrir(
                    context,
                    jogo.getEndereco().getAddress()
                            + ", "
                            + jogo.getEndereco().getCity()
                            + ", "
                            + jogo.getEndereco().getState()
            );
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            builder.include(latLng);
            this.mapa.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title(jogo.getTime().getNome())
            );
        }

        LatLngBounds bounds = builder.build();
        this.mapa.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_jogador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_jogador_time_menu_filtrar:
                filtrarPosicao();
                return true;
            case R.id.menu_home_jogador_menu_limpar:
                limparPosicao();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filtrarPosicao() {
        Intent i = new Intent(HomeJogador.this, Filtro.class);
        i.putExtra("goto", Filtro.GOTO_HOME_JOGADOR);
        i.putExtra("Jogador", this.jogador);
        startActivity(i);
    }

    private void limparPosicao() {
        this.posicoes = null;
        this.initJogosList();
        this.onMapReady(this.mapa);
    }
}
