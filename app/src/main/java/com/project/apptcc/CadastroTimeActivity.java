package com.project.apptcc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class CadastroTimeActivity extends DebugActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_time);

        // Adiciona o botão "up navigation"
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spTipos = (Spinner) findViewById(R.id.spnTipoJogo);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.tipos,android.R.layout.simple_spinner_item);
        spTipos.setAdapter(adapter);

        Button btFindMatch = (Button) findViewById(R.id.btnCadastrarTime);


    }


    private Context getContext() {

        return this;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            // O método finish() vai encerrar essa activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
