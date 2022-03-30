package com.example.scaneco.horrampoubelles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.pointdecollecte.RecherchePointDeCollecte;
import com.google.android.material.bottomnavigation.BottomNavigationView;


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


        //Initialisation de la barre de menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            int itemId = item.getItemId();
            if (itemId == R.id.accueilHorRamPoubelles) {
                ouvrirHorRamPoubelles();
            } else if (itemId == R.id.accueilAnimations) {
                ouvrirAnimations();
            } else if (itemId == R.id.accueilPointDeCollecte) {
                ouvrirRecherchePointDeCollecte();
            }
            return true;
        });

        //Bouton de retour
        ImageButton boutonRetourScan = findViewById(R.id.boutonRetourScan);
        boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());    }

    /////////////////Navigation\\\\\\\\\\\\\\\\\\\\

    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder aux Animations
     **/
    public void ouvrirAnimations()
    {
        Intent intent = new Intent(this, AccueilAnimations.class);
        startActivity(intent);
    }

    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder à la page des horaires de ramassage des poubelles
     **/
    public void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }
    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder à la page des points de collecte
     **/
    protected void ouvrirRecherchePointDeCollecte()
    {
        Intent intent = new Intent(this, RecherchePointDeCollecte.class);
        startActivity(intent);
    }

    protected void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}