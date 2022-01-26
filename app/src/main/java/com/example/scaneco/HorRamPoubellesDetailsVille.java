package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HorRamPoubellesDetailsVille extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_ram_poubelles_details_ville);

        TextView textView;
        textView=findViewById(R.id.NomDeLaVille);

        String nomVilleRecuperee= getIntent().getStringExtra("valRecup");
        textView.setText(nomVilleRecuperee);
    }
}