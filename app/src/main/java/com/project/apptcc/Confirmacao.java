package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

import Objetos.Jogador;
import Objetos.Jogo;
import Objetos.Time;

public class Confirmacao extends Activity {

    public static int CONFIRMACAO_JOGA_PRA_GENTE = 0;
    public static int CONFIRMACAO_QUERO_JOGAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);
        getActionBar().setDisplayHomeAsUpEnabled(true);



        final Intent i;
        try {
            i = this.setarParametrosIntent(this.carregarInstanciaIntent());

            this.redirecionar(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent carregarInstanciaIntent() throws Exception {
        int flag = getIntent().getIntExtra("flag", 0);
        if (flag == this.CONFIRMACAO_JOGA_PRA_GENTE) {
            return new Intent(Confirmacao.this, HomeTime.class);
        } else if (flag == this.CONFIRMACAO_QUERO_JOGAR) {
            return new Intent(Confirmacao.this, HomeJogador.class);
        }

        throw new Exception("Flag inválida");
    }

    private Intent setarParametrosIntent(Intent i) {
        if (getIntent().hasExtra("Jogador")) {
            final Jogador jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
            i.putExtra("Jogador", jogador);
        }
        if (getIntent().hasExtra("Time")) {
            final Time time = (Time) getIntent().getSerializableExtra("Time");
            i.putExtra("Time", time);
        }
        if (getIntent().hasExtra("Jogo")) {
            final Jogo jogo = (Jogo) getIntent().getSerializableExtra("Jogo");
            i.putExtra("Jogo", jogo);
        }

        return i;
    }

    private void redirecionar(final Intent i) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(i);
                    }
                },
                5000);
    }

}
