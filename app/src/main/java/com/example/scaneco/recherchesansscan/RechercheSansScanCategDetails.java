package com.example.scaneco.recherchesansscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scaneco.R;

public class RechercheSansScanCategDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_sans_scan_categ_details);

        TextView nomEmballage = findViewById(R.id.textView_nomEmballage);
        ImageView imageEmballage = findViewById(R.id.imageView_imageEmballage);
        ImageView imagePoubelle = findViewById(R.id.imageView_imagePoubelle);
        TextView texteExplicatif = findViewById(R.id.textExplicatif);
        if(getIntent() != null){
            String info = getIntent().getStringExtra("info");

            nomEmballage.setText(info);
            switch(info)
            {
                case "Papiers" : imageEmballage.setImageResource(R.drawable.papier);
                                 imagePoubelle.setImageResource(R.drawable.poubelle_bleue);
                                break;
                case "Verres" :
                    imageEmballage.setImageResource(R.drawable.duvin);
                    imagePoubelle.setImageResource(R.drawable.poubelle_verte);
                    texteExplicatif.setText(R.string.verre);
                                break;
                case "Plastiques" :
                    imageEmballage.setImageResource(R.drawable.plastique);
                    imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    texteExplicatif.setText(R.string.plastique);
                    break;
                case "Piles" :
                    imageEmballage.setImageResource(R.drawable.batterie);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);
                    texteExplicatif.setText(R.string.pile);
                    break;
                case "Fruits et Légumes" :
                    imageEmballage.setImageResource(R.drawable.la_nourriture_saine);
                    imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    texteExplicatif.setText(R.string.compost);
                    break;
                case "Verdures" :
                    imageEmballage.setImageResource(R.drawable.herbe);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);
                    texteExplicatif.setText(R.string.verdure);
                    break;
                case "Vaisselles" :
                    imageEmballage.setImageResource(R.drawable.assiette);
                    imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    texteExplicatif.setText(R.string.vaisselle);
                    break;
                case "Médicament" :
                    imageEmballage.setImageResource(R.drawable.medicament);
                    imagePoubelle.setImageResource(R.drawable.pharmacie);
                    texteExplicatif.setText(R.string.medicament);
                    break;
                case "Cartons" :
                    imageEmballage.setImageResource(R.drawable.papiercarton);
                    imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    texteExplicatif.setText(R.string.carton);
                    break;
                case "Vêtements" :
                    imageEmballage.setImageResource(R.drawable.vetements);
                    imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    texteExplicatif.setText(R.string.Vetements);
                    break;
                case "Meubles" :
                    imageEmballage.setImageResource(R.drawable.meubles);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);
                    texteExplicatif.setText(R.string.Meubles);
                    break;
                case "Electroménager" :
                    imageEmballage.setImageResource(R.drawable.electromenager);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);

                    texteExplicatif.setText(R.string.Electromenager);
                    break;
                default:
                    break;
            }
        }
    }
}