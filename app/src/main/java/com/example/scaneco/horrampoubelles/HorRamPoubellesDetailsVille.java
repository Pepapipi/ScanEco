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


        String nomVilleRecuperee = villeRecuperee.nom;
        textView.setText(nomVilleRecuperee);


        if (villeRecuperee.codePostal.equals("64600")||
                villeRecuperee.codePostal.equals("64200")||
                villeRecuperee.codePostal.equals("64100")||
                villeRecuperee.codePostal.equals("64340")||villeRecuperee.codePostal.equals("64210"))
        {
            findViewById(R.id.cartographie);
        }

        if (villeRecuperee.codePostal.equals("64990"))
        {
            findViewById(R.id.cartographie);
        }


        textViewJoursSelectifs.setText(villeRecuperee.joursSelectifs);
        textViewJoursMenagers.setText(villeRecuperee.joursMenagers);
        textViewHeuresSelectifs.setText(villeRecuperee.heuresSelectifs);
        textViewHeuresMenagers.setText(villeRecuperee.heuresMenagers);


    }
}