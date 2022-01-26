package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilRechercheSansScan extends AppCompatActivity {
    private Button _boutonRetourScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_recherche_sans_scan);
        _boutonRetourScan = (Button) findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirLeScanneur();
            }
        });
    }

    /*Fonction qui permet d'accéder au scanneur */
    public void ouvrirLeScanneur(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}