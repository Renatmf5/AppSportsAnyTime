package com.project.apptcc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

public class Confirmacao extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
