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
                // TODO changer les setText par de ressources android
                case "Papiers" : imageEmballage.setImageResource(R.drawable.papier);
                                 imagePoubelle.setImageResource(R.drawable.poubelle_bleue);
                                break;
                case "Verres" :
                    imageEmballage.setImageResource(R.drawable.duvin);
                    imagePoubelle.setImageResource(R.drawable.poubelle_verte);
                    texteExplicatif.setText("Les bouteilles et autres contenant en verre vont dans la poubelle des verres");
                                break;
                case "Plastiques" :
                    imageEmballage.setImageResource(R.drawable.plastique);
                    imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    texteExplicatif.setText("Les plastiques recyclabes tels que les bouteilles en plastiques vont dans la poubelle jaune");
                    break;
                case "Piles" :
                    imageEmballage.setImageResource(R.drawable.batterie);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);
                    texteExplicatif.setText("Les conteneurs de piles peuvent être aussi dans les hypermarchés");
                    break;
                case "Fruits et Légumes" :
                    imageEmballage.setImageResource(R.drawable.la_nourriture_saine);
                    imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    texteExplicatif.setText("Si vous avez un conteneur avec du compost mettez les plutôt là dedans");
                    break;
                case "Verdures" :
                    imageEmballage.setImageResource(R.drawable.herbe);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);
                    texteExplicatif.setText("La verdure comme le gazon vont en déchetterie");
                    break;
                case "Vaisselles" :
                    imageEmballage.setImageResource(R.drawable.assiette);
                    imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    texteExplicatif.setText("La vaisselle se jette dans la poubelle des déchets ménagers");
                    break;
                case "Médicament" :
                    imageEmballage.setImageResource(R.drawable.medicament);
                    imagePoubelle.setImageResource(R.drawable.pharmacie);
                    texteExplicatif.setText("Tout médicament se ramène en pharmacie !");
                    break;
                case "Cartons" :
                    imageEmballage.setImageResource(R.drawable.papiercarton);
                    imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    texteExplicatif.setText("Les petits emballages en carton vont dans la poubelle jaune, sinon c'est à la déchetterie");
                    break;
                case "Vêtements" :
                    imageEmballage.setImageResource(R.drawable.vetements);
                    imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    texteExplicatif.setText("S'ils sont en bon état veuillez les donner à une association");
                    break;
                case "Meubles" :
                    imageEmballage.setImageResource(R.drawable.meubles);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);
                    texteExplicatif.setText("S'ils sont en bon état veuillez les donner à une association");
                    break;
                case "Electroménager" :
                    imageEmballage.setImageResource(R.drawable.electromenager);
                    imagePoubelle.setImageResource(R.drawable.dechetterie);

                    texteExplicatif.setText("S'ils sont en bon état veuillez les donner à une association");
                    break;
                default:
                    break;
            }
        }
    }
}