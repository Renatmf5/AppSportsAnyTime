package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.jdeferred.DoneCallback;
import org.jdeferred.Promise;

import Objetos.Jogador;
import Objetos.Time;

public class Review extends Activity {

    final Review activity = this;
    private Jogador jogador = null;
    private Time time = null;
    private TextView textViewNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        this.textViewNome = (TextView)findViewById(R.id.textViewNome);
        if (getIntent().hasExtra("JogadorAvaliado")) {
            this.jogador = (Jogador) getIntent().getSerializableExtra("JogadorAvaliado");
            this.time = (Time) getIntent().getSerializableExtra("TimeAvaliador");
            this.textViewNome.setText(this.jogador.getNome());
        }
        if (getIntent().hasExtra("TimeAvaliado")) {
            this.jogador = (Jogador) getIntent().getSerializableExtra("JogadorAvaliador");
            this.time = (Time) getIntent().getSerializableExtra("TimeAvaliado");
            this.textViewNome.setText(this.time.getNome());
        }

    }

    public void enviar(View view) {
        Promise promise = null;
        if (this.time != null) {
            promise = this.enviarAvaliacaoTime();
        } else if (this.jogador != null) {
            promise = this.enviarAvaliacaoJogador();
        }

        if (promise != null) {
            promise.done(new DoneCallback() {
                @Override
                public void onDone(Object result) {
                    activity.redirecionarParaConfirmacao();
                }
            });
        }
    }

    private Promise enviarAvaliacaoTime() {
        return Services.Time.Avaliacao.Criador.execute(this, this.time, criarReview());
    }

    private Promise enviarAvaliacaoJogador() {
        return Services.Jogador.Avaliacao.Criador.execute(this, this.jogador, criarReview());
    }

    private Objetos.Review criarReview() {
        return new Objetos.Review(
                ((RatingBar)findViewById(R.id.ratingBarReview)).getNumStars(),
                ((EditText)findViewById(R.id.editTextReview)).getText().toString()
        );
    }

    private void redirecionarParaConfirmacao() {
        Intent intent = new Intent(activity, Confirmacao.class);
        intent.putExtra("flag", getIntent().getIntExtra("flag", 0));
        intent.putExtra("Jogador", this.jogador);
        intent.putExtra("Time", this.time);
        startActivity(intent);
    }
}
