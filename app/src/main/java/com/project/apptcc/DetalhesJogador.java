package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Review;
import Objetos.Time;
import Services.Jogador.Avaliacao.Listador;

public class DetalhesJogador extends AppCompatActivity {
    DetalhesJogador context = this;
    Jogador jogador;
    Time time;
    ListView listView;
    ArrayList<Review> listItems = new ArrayList<Review>();
    ArrayAdapter<Review> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogador);

        this.jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
        this.time = (Time) getIntent().getSerializableExtra("Time");
        ((TextView)findViewById(R.id.textViewNameJogador)).setText(jogador.getNome());
        ((TextView)findViewById(R.id.textViewPositionJogador)).setText(jogador.getPosicaoValueStr());

        this.initListReviewsJogador();
    }

    private void initListReviewsJogador() {
        this.listView = (ListView)findViewById(R.id.listReviewsJogador);
        this.adapter = new ArrayAdapter<Review>(
                this,
                android.R.layout.simple_list_item_1,
                this.listItems
        );
        this.listView.setAdapter(adapter);

        Listador.execute(this, this.jogador)
                .done(this.buildDoneCallback())
                .fail(this.buildFailCallback());
    }

    private DoneCallback buildDoneCallback() {
        return new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONArray data = ((JSONArray)result);
                    for (int i = 0; i < data.length(); i++) {
                        Review review = new Review(data.getJSONObject(i));
                        context.listItems.add(review);
                    }
                    context.adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private FailCallback buildFailCallback() {
        return new FailCallback() {
            @Override
            public void onFail(Object result) {
                Toast.makeText(
                        context,
                        "Ocorreu um erro ao recuperar as avaliações do jogador. Por favor, tente novamente.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
    }

    public void jogaPraGente(View view) {
        Services.Time.Jogador.Criador.execute(DetalhesJogador.this, this.time, this.jogador)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        Intent i = new Intent(DetalhesJogador.this, Confirmacao.class);
                        i.putExtra("flag", Confirmacao.CONFIRMACAO_JOGA_PRA_GENTE);
                        i.putExtra("Time", DetalhesJogador.this.time);
                        i.putExtra("Jogador", DetalhesJogador.this.jogador);
                        startActivity(i);
                    }
                })
                .fail(new FailCallback() {
                    @Override
                    public void onFail(Object result) {
                        Toast.makeText(DetalhesJogador.this, "Ocorreu um erro ao pedir para o " +
                                DetalhesJogador.this.jogador.getNome() + " jogar para vocês. " +
                                "Por favor, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void avaliar(View view) {
        Intent i = new Intent(DetalhesJogador.this, com.project.apptcc.Review.class);
        i.putExtra("flag", Confirmacao.CONFIRMACAO_JOGA_PRA_GENTE);
        i.putExtra("TimeAvaliador", this.time);
        i.putExtra("JogadorAvaliado", this.jogador);
        startActivity(i);
    }
}