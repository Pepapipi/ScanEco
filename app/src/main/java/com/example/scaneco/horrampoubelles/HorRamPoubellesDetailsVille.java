package com.example.scaneco.horrampoubelles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.scaneco.R;


public class HorRamPoubellesDetailsVille extends AppCompatActivity {


    static Ville villeRecuperee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_ram_poubelles_details_ville);

        TextView textViewJoursSelectifs = findViewById(R.id.joursSelectifs);
        TextView textViewJoursMenagers = findViewById(R.id.joursMenagers);
        TextView textViewHeuresSelectifs = findViewById(R.id.heuresSelectifs);
        TextView textViewHeuresMenagers = findViewById(R.id.heuresMenagers);

        TextView textView;

        textView = findViewById(R.id.NomDeLaVille);


        String nomVilleRecuperee = villeRecuperee.getNom();
        textView.setText(nomVilleRecuperee);


        if (villeRecuperee.getCodePostal().equals("64600")||
                villeRecuperee.getCodePostal().equals("64200")||
                villeRecuperee.getCodePostal().equals("64100")||
                villeRecuperee.getCodePostal().equals("64340")|| villeRecuperee.getCodePostal().equals("64210"))
        {
            findViewById(R.id.cartographie);
        }

        if (villeRecuperee.getCodePostal().equals("64990"))
        {
            findViewById(R.id.cartographie);
        }


        textViewJoursSelectifs.setText(villeRecuperee.getJoursSelectifs());
        textViewJoursMenagers.setText(villeRecuperee.getJoursMenagers());
        textViewHeuresSelectifs.setText(villeRecuperee.getHeuresSelectifs());
        textViewHeuresMenagers.setText(villeRecuperee.getHeuresMenagers());


    }
}