package com.project.apptcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Objetos.Review;


public class DetalhesJogador extends Activity {
    ArrayList<Review> listItems=new ArrayList<Review>();
    ArrayAdapter<Review> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_time);

        this.initListReviews();
    }

    private void initListReviews() {
        ListView listReviews = findViewById(R.id.listReviews);
        listReviews.setAdapter(adapter);
    }
}