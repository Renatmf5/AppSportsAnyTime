package com.project.apptcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jdeferred.DoneCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Review;


public class DetalhesJogador extends Activity {
    Jogador jogador;
    ArrayList<Review> listItems=new ArrayList<Review>();
    ArrayAdapter<Review> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_jogo);

        this.jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
        ((TextView)findViewById(R.id.textViewNameJogador)).setText(jogador.getNome());

        this.initListReviews();
    }

    private void initListReviews() {
        ListView listReviews = findViewById(R.id.listReviews);
        listReviews.setAdapter(adapter);

        Services.Jogador.Review.Listador.execute(this.jogador).done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONArray data = ((JSONObject)result).getJSONArray("data");
                    for (int i = 0; i <= data.length(); i++) {
                        listItems.add(
                                new Review(data.getJSONObject(i))
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}