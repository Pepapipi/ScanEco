package com.example.scaneco.animations;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URL;


public class AccueilAnimations extends AppCompatActivity {


    private ImageButton _boutonRetourScan;
    private VideoView _videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_animations);

        //BARRE DE NAVIGATION
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



        //VIDEOS

        _videoView = findViewById(R.id.videoView);
        _videoView.setVideoPath("https://youtu.be/REh-GAV1cfA");
        _videoView.start();

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

    /*
    Reprendre l'affichage de l'activité
     */
    @Override
    protected void onResume(){
        super.onResume();
        _videoView.start();
    }

    /*
    Suspend l'execution de la video
     */
    @Override
    protected void onPause(){
        super.onPause();
        _videoView.suspend();
    }
}