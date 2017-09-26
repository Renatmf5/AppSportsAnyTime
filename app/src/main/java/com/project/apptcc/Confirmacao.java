package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

import Objetos.Jogador;
import Objetos.Jogo;
import Objetos.Time;

public class Confirmacao extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("Jogador")) {
            final Jogador jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(Confirmacao.this, HomeJogador.class);
                            i.putExtra("Jogador", jogador);
                            startActivity(i);
                        }
                    },
                    1000);
        }
        if (getIntent().hasExtra("Time")) {
            final Time time = (Time) getIntent().getSerializableExtra("Time");
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(Confirmacao.this, HomeTime.class);
                            i.putExtra("Time", time);
                            startActivity(i);
                        }
                    },
                    1000);
        }
    }

}
