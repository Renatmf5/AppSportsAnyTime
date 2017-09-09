package com.project.apptcc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class CadastroJogadorActivity extends AppCompatActivity {

    private android.support.v4.app.FragmentManager fragmetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spTipos = (Spinner) findViewById(R.id.spnTipoJogo);
        Spinner spPosicoes = (Spinner) findViewById(R.id.spnPosicao);
        ArrayAdapter adapterPosicoes = ArrayAdapter.createFromResource(this,R.array.posicoes,android.R.layout.simple_spinner_item);
        ArrayAdapter adapterTipos = ArrayAdapter.createFromResource(this,R.array.tipos,android.R.layout.simple_spinner_item);
        spPosicoes.setAdapter(adapterPosicoes);
        spTipos.setAdapter(adapterTipos);
    }


    private Context getContext() {

        return this;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
