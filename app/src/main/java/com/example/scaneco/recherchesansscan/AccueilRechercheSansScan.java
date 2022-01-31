package com.example.scaneco.recherchesansscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.GridLayout;
import android.widget.ImageButton;


import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilRechercheSansScan extends AppCompatActivity {

    private ImageButton _boutonRetourScan;
    private GridLayout _gridLayout;
    ArrayList<String> arrayEmballage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_recherche_sans_scan);

        _gridLayout = (GridLayout)findViewById(R.id.gridViewRechercheSansScan);
        setSingleEvent(_gridLayout);
        //Barre de navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.accueilHorRamPoubelles:
                    ouvrirHorRamPoubelles();
                    break;

            }
            return true;
        });
        //Bouton de retour
        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ouvrirLeScan();
            }
        });



    }

    protected void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }

    protected void setSingleEvent (GridLayout mainGrid){
        arrayEmballage.addAll(Arrays.asList(getResources().getStringArray(R.array.mes_dechets)));

        for (int i = 0; i< mainGrid.getChildCount(); i++){
            CardView _cardView = (CardView) mainGrid.getChildAt(i);
            final int _finalI = i;
            _cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(AccueilRechercheSansScan.this, RechercheSansScanCategDetails.class);
                intent.putExtra("info", arrayEmballage.get(_finalI));
                startActivity(intent);
                }
            });
        }
    }
}