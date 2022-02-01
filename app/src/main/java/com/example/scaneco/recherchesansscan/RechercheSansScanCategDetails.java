package com.example.scaneco.recherchesansscan;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scaneco.R;

public class RechercheSansScanCategDetails extends AppCompatActivity {

    private TextView _nomEmballage;
    private ImageView _imageEmballage;
    private ImageView _imagePoubelle;
    private TextView _texteExplicatif;
    private Drawable _test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_sans_scan_categ_details);

        _nomEmballage = (TextView)findViewById(R.id.textView_nomEmballage);
        _imageEmballage = (ImageView) findViewById(R.id.imageView_imageEmballage);
        _imagePoubelle = (ImageView) findViewById(R.id.imageView_imagePoubelle);
        _texteExplicatif = (TextView) findViewById(R.id.textExplicatif);
        if(getIntent() != null){
            String info = getIntent().getStringExtra("info");

            _nomEmballage.setText(info);
            switch(info)
            {
                case "Papiers" : _imageEmballage.setImageResource(R.drawable.papier);
                                 _imagePoubelle.setImageResource(R.drawable.poubelle_bleue);
                                break;
                case "Verres" :
                    _imageEmballage.setImageResource(R.drawable.duvin);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_verte);
                    _texteExplicatif.setText("Les bouteilles et autres contenant en verre vont dans la poubelle des verres");
                                break;
                case "Plastiques" :
                    _imageEmballage.setImageResource(R.drawable.plastique);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    _texteExplicatif.setText("Les plastiques recyclabes tels que les bouteilles en plastiques vont dans la poubelle jaune");
                    break;
                case "Piles" :
                    _imageEmballage.setImageResource(R.drawable.batterie);
                    _imagePoubelle.setImageResource(R.drawable.dechetterie);
                    _texteExplicatif.setText("Les conteneurs de piles peuvent être aussi dans les hypermarchés");
                    break;
                case "Fruits et Légumes" :
                    _imageEmballage.setImageResource(R.drawable.la_nourriture_saine);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    _texteExplicatif.setText("Si vous avez un conteneur avec du compost mettez les plutôt là dedans");
                    break;
                case "Verdures" :
                    _imageEmballage.setImageResource(R.drawable.herbe);
                    _imagePoubelle.setImageResource(R.drawable.dechetterie);
                    _texteExplicatif.setText("La verdure comme le gazon vont en déchetterie");
                    break;
                case "Vaisselles" :
                    _imageEmballage.setImageResource(R.drawable.assiette);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    _texteExplicatif.setText("La vaisselle se jette dans la poubelle des déchets ménagers");
                    break;
                case "Médicament" :
                    _imageEmballage.setImageResource(R.drawable.medicament);
                    _imagePoubelle.setImageResource(R.drawable.pharmacie);
                    _texteExplicatif.setText("Tout médicament se ramène en pharmacie !");
                    break;
                case "Cartons" :
                    _imageEmballage.setImageResource(R.drawable.papiercarton);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    _texteExplicatif.setText("Les petits emballages en carton vont dans la poubelle jaune, sinon c'est à la déchetterie");
                    break;
                case "Vêtements" :
                    _imageEmballage.setImageResource(R.drawable.vetements);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_noire);
                    _texteExplicatif.setText("S'ils sont en bon état veuillez les donner à une association");
                    break;
                case "Meubles" :
                    _imageEmballage.setImageResource(R.drawable.meubles);
                    _imagePoubelle.setImageResource(R.drawable.dechetterie);
                    _texteExplicatif.setText("S'ils sont en bon état veuillez les donner à une association");
                    break;
                case "Electroménager" :
                    _imageEmballage.setImageResource(R.drawable.electromenager);
                    _imagePoubelle.setImageResource(R.drawable.dechetterie);

                    _texteExplicatif.setText("S'ils sont en bon état veuillez les donner à une association");
                    break;
            }
        }
    }
}