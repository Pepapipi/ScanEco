package com.example.scaneco.animations;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AccueilAnimations extends AppCompatActivity {


    private ImageButton _boutonRetourScan;
    private VideoView _videoView;
    private VideoView _video2;


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
        String videoPath = "android.resource://"+getPackageName()+"/"+R.raw.amogus;
        Uri uri = Uri.parse(videoPath);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(_videoView);
        _videoView.setMediaController(mediaController);
        _videoView.setVideoURI(uri);

        _video2 = findViewById(R.id.videoView2);
        String videoPath2 = "android.resource://"+getPackageName()+"/"+R.raw.widescaneco;
        Uri uri2 = Uri.parse(videoPath2);
        MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(_video2);
        _video2.setMediaController(mediaController2);
        _video2.setVideoURI(uri2);


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


}