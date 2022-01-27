package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HorRamPoubellesDetailsVille extends AppCompatActivity {
    private ImageButton _boutonRetourScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_ram_poubelles_details_ville);

        TextView textView;
        textView=findViewById(R.id.NomDeLaVille);

        String nomVilleRecuperee= getIntent().getStringExtra("valRecup");
        textView.setText(nomVilleRecuperee);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.accueilHorRamPoubelles:
                    ouvrirHorRamPoubelles();
                    break;

            }
            return true;
        });

        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ouvrirLeScan();
            }
        });
    }

    public void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }
}