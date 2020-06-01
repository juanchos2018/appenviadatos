package com.document.enviadatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Mapa extends AppCompatActivity {


    String la,lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        MapaFragment fr = new MapaFragment();
        Bundle args = new Bundle();
        String texto="hola we";


        // Colocamos el String
        args.putString("textFromActivityB", texto);
        fr.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.contenerdor,fr).commit();
    }
}
