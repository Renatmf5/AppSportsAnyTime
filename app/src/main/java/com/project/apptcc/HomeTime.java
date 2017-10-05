package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Time;
import Services.Endereco.DescobrirLatLong;
import Services.Endereco.GeoPoint;
import Services.Jogador.Listador;

public class HomeTime extends FragmentActivity implements OnMapReadyCallback {
    private HomeTime context = this;
    private Time time;
    private ListView listView;
    private ArrayList<String> posicoes = new ArrayList<>();
    private ArrayList<Jogador> listItems = new ArrayList<Jogador>();
    private ArrayAdapter<Jogador> adapter;
    private GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_time);

        if (getIntent().hasExtra("posicoes")) {
            this.posicoes = getIntent().getStringArrayListExtra("posicoes");
        }
        this.time = (Time)getIntent().getSerializableExtra("Time");
        this.initJogadoresList();
    }

    private void initJogadoresList() {
        this.listView = (ListView) findViewById(R.id.listJogadores);
        this.adapter = new ArrayAdapter<Jogador>(
                this,
                android.R.layout.simple_list_item_1,
                this.listItems
        );
        this.listView.setAdapter(this.adapter);

        Listador.execute(this, this.posicoes).done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONArray data = ((JSONArray)result);
                    for (int i = 0; i < data.length(); i++) {
                        Jogador jogador = new Jogador(data.getJSONObject(i));
                        context.listItems.add(jogador);
                    }
                    context.adapter.notifyDataSetChanged();
                    context.initMap();
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
                i.putExtra("Time", time);
                startActivity(i);
            }
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapJogadores);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Jogador jogador : this.listItems) {
            GeoPoint point = DescobrirLatLong.descobrir(
                    context,
                    jogador.getEndereco().getAddress()
                            + ", "
                            + jogador.getEndereco().getCity()
                            + ", "
                            + jogador.getEndereco().getState()
            );
            if (point != null) {
                LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                builder.include(latLng);
                this.mapa.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title(jogador.getNome())
                );

                LatLngBounds bounds = builder.build();
                this.mapa.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            }
        }
    }

    public void abrirNovoJogo(View view) {
        Intent i = new Intent(HomeTime.this, CadastroJogo.class);
        i.putExtra("Time", this.time);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_home_time_menu_filtrar:
                filtrarPosicao();
                return true;
            case R.id.menu_home_time_menu_limpar:
                limparPosicao();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filtrarPosicao() {
        Intent i = new Intent(HomeTime.this, Filtro.class);
        i.putExtra("goto", Filtro.GOTO_HOME_TIME);
        i.putExtra("Time", this.time);
        startActivity(i);
    }

    private void limparPosicao() {
        this.posicoes = null;
    }
}
