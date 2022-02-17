package com.example.scaneco.animations;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
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
        _boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());



        //VIDEOS

        String videoKey = "REh-GAV1cfA";
        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(v -> {
            Log.println(Log.INFO, "info", "redirection");
            Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoKey));
            youtube.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            youtube.setPackage("com.google.android.youtube");
            try {
                getApplication().startActivity(youtube);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        try {
            new SetImageFromUrl(imageView, "https://img.youtube.com/vi/" + videoKey + "/0.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }
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