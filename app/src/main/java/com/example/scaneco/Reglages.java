package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class Reglages extends AppCompatActivity {

    private ImageButton _boutonFlecheRetourScan;
    private CompoundButton _switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglages);

        // initializing all our variables.
        _switch = findViewById(R.id.switchSombreClair);
        _boutonFlecheRetourScan = findViewById(R.id.boutonFlecheRetourScan);

        //Lorsqu'on clique sur la flèche de retour, on revient sur la page de scan
        _boutonFlecheRetourScan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ouvrirLeScan();
            }
        });

        //Activation du mode sombre ou non
        _switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Si le switch est activé alors on active le mode sombre
                if (isChecked) {
                    //Permet de mettre le mode sombre
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                //Sinon on active pas le mode sombre (on reste sur le mode clair)
                } else {
                    //Permet de ne pas mettre le mode sombre
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

    }

    public void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}