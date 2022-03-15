package com.example.scaneco.animations;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.example.scaneco.pointDeCollecte.RecherchePointDeCollecte;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AccueilAnimations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_animations);
        //BARRE DE NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.accueilHorRamPoubelles) {
                ouvrirHorRamPoubelles();
            } else if (itemId == R.id.accueilPointDeCollecte) {
                ouvrirRecherchePointDeCollecte();
            }
            return true;
        });
        //Bouton de retour
        ImageButton boutonRetourScan = findViewById(R.id.boutonRetourScan);
        boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());



        //VIDEOS
        String messageRedirection = "redirection";
        String baseUrlYoutubeVideo = "https://www.youtube.com/watch?v=";
        String packageYoutube = "com.google.android.youtube";
        String baseUrlYoutubeThumbnail = "https://img.youtube.com/vi/";
        String imgExtention = "/0.jpg";
        String videoKey = "REh-GAV1cfA";
        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setOnClickListener(v -> {
            Log.println(Log.INFO, "info", messageRedirection);
            Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrlYoutubeVideo + videoKey));
            youtube.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            youtube.setPackage(packageYoutube);
            try {
                getApplication().startActivity(youtube);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        try {
            new SetImageFromUrl(imageView, baseUrlYoutubeThumbnail + videoKey + imgExtention);
        }catch (Exception e){
            e.printStackTrace();
        }

        String videoKey2 = "MECmgIz36nU";
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(v -> {
            Log.println(Log.INFO, "info", messageRedirection);
            Intent youtube2 = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrlYoutubeVideo + videoKey2));
            youtube2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            youtube2.setPackage(packageYoutube);
            try {
                getApplication().startActivity(youtube2);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        try {
            new SetImageFromUrl(imageView2, baseUrlYoutubeThumbnail + videoKey2 + imgExtention);
        }catch (Exception e){
            e.printStackTrace();
        }

        String videoKey3 = "w6WTuAl18CA";
        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(v -> {
            Log.println(Log.INFO, "info", messageRedirection);
            Intent youtube3 = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrlYoutubeVideo + videoKey3));
            youtube3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            youtube3.setPackage(packageYoutube);
            try {
                getApplication().startActivity(youtube3);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        try {
            new SetImageFromUrl(imageView3, baseUrlYoutubeThumbnail + videoKey3 + imgExtention);
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
    protected void ouvrirRecherchePointDeCollecte()
    {
        Intent intent = new Intent(this, RecherchePointDeCollecte.class);
        startActivity(intent);
    }

}