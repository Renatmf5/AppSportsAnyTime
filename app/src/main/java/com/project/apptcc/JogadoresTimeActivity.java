package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Time;

public class JogadoresTimeActivity extends Activity {
    private JogadoresTimeActivity context = this;
    private Time time;
    private ListView listView;
    private ArrayList<Jogador> listItems = new ArrayList<Jogador>();
    private ArrayAdapter<Jogador> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadores_time);

        this.time = (Time)getIntent().getSerializableExtra("Time");
        this.initJogadoresList();
    }

    private void initJogadoresList() {
        this.listView = (ListView) findViewById(R.id.listJogadoresConvidados);
        this.adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                this.listItems
        );
        this.listView.setAdapter(this.adapter);

        Services.Time.Jogador.Listador.execute(this, this.time).done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONArray data = ((JSONArray)result);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject teamPlayer = data.getJSONObject(i);
                        Jogador jogador = new Jogador(teamPlayer.getJSONObject("player"));
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
    }

}
