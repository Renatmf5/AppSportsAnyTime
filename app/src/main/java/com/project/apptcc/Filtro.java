package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Time;

public class Filtro extends Activity {



    public static final int GOTO_HOME_TIME = 0;
    public static final int GOTO_HOME_JOGADOR = 1;
    ArrayList<String> filters = new ArrayList<>();
    private int filterGoto;
    private Time time;
    private Jogador jogador;
    private MultiSpinner spinner;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> selectedPosicoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        this.filterGoto = getIntent().getIntExtra("goto", 0);
        if (getIntent().hasExtra("Time")) {
            this.time = (Time)getIntent().getSerializableExtra("Time");
        }
        if (getIntent().hasExtra("Jogador")) {
            this.jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
        }
        this.initSpinnerPosicoes();
    }

    public void filtrar(View view) throws Exception {
        Intent i = this.buildRedirectionIntent();
        if (getIntent().hasExtra("Time")) {
            i.putExtra("time", this.time);
        }
        if (getIntent().hasExtra("Jogador")) {
            i.putExtra("jogador", this.jogador);
        }
        i.putExtra("posicoes", this.selectedPosicoes);
        startActivity(i);
    }

    private void initSpinnerPosicoes() {
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.posicoesValue)
        );

        spinner = (MultiSpinner) findViewById(R.id.spnFiltroPosicoes);
        spinner.setAdapter(adapter, false, onSelectedListener);
    }

    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        @Override
        public void onItemsSelected(boolean[] selected) {
            selectedPosicoes.clear();
            String[] keys = getResources().getStringArray(R.array.posicoesKey);
            for (int i = 0; i < keys.length; i++) {
                if (selected[i]) {
                    selectedPosicoes.add(keys[i]);
                }
            }
        }
    };

    private Intent buildRedirectionIntent() throws Exception {
        switch (this.filterGoto) {
            case GOTO_HOME_TIME:
                return new Intent(Filtro.this, HomeTime.class);
            case GOTO_HOME_JOGADOR:
                return new Intent(Filtro.this, HomeJogador.class);
            default:
                throw new Exception("Invalid goto parameter");
        }
    }

}
