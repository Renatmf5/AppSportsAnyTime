package com.project.apptcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import Objetos.Jogador;
import Objetos.Time;

public class FiltroPosicao extends AppCompatActivity {

    public static final int GOTO_HOME_TIME = 0;
    public static final int GOTO_HOME_JOGADOR = 1;
    ArrayList<String> filters = new ArrayList<>();
    private int filterGoto;
    private Time time;
    private Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_posicao);

        this.filterGoto = getIntent().getIntExtra("goto", 0);
        if (getIntent().hasExtra("Time")) {
            this.time = (Time)getIntent().getSerializableExtra("Time");
        }
        if (getIntent().hasExtra("Jogador")) {
            this.jogador = (Jogador) getIntent().getSerializableExtra("Jogador");
        }

        initCheckboxesListeners();
    }

    public void filtrar(View view) throws Exception {
        Intent i = this.buildRedirectionIntent();
        if (getIntent().hasExtra("Time")) {
            i.putExtra("time", this.time);
        }
        if (getIntent().hasExtra("Jogador")) {
            i.putExtra("jogador", this.jogador);
        }
        i.putExtra("posicoes", this.filters);
        startActivity(i);
    }

    private Intent buildRedirectionIntent() throws Exception {
        switch (this.filterGoto) {
            case GOTO_HOME_TIME:
                return new Intent(FiltroPosicao.this, HomeTime.class);
            case GOTO_HOME_JOGADOR:
                return new Intent(FiltroPosicao.this, HomeJogador.class);
            default:
                throw new Exception("Invalid goto parameter");
        }
    }

    private void initCheckboxesListeners() {
        ((CheckBox)findViewById(R.id.filtrar_gol)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("gol")
        );
        ((CheckBox)findViewById(R.id.filtrar_ld)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("ld")
        );
        ((CheckBox)findViewById(R.id.filtrar_le)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("le")
        );
        ((CheckBox)findViewById(R.id.filtrar_zag)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("zag")
        );
        ((CheckBox)findViewById(R.id.filtrar_vol)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("vol")
        );
        ((CheckBox)findViewById(R.id.filtrar_mei)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("mei")
        );
        ((CheckBox)findViewById(R.id.filtrar_ata)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("ata")
        );
        ((CheckBox)findViewById(R.id.filtrar_fixo)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("fixo")
        );
        ((CheckBox)findViewById(R.id.filtrar_ala)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("ala")
        );
        ((CheckBox)findViewById(R.id.filtrar_pivo)).setOnCheckedChangeListener(
                this.buildOnCheckedChangeListener("pivo")
        );
    }

    private CompoundButton.OnCheckedChangeListener buildOnCheckedChangeListener(final String position) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked && filters.indexOf(position) == -1) {
                    filters.add(position);
                } else if (!isChecked && filters.indexOf(position) > -1) {
                    filters.remove(position);
                }
            }
        };
    }
}
