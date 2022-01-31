package com.example.scaneco.recherchesansscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scaneco.R;

public class RechercheSansScanCategDetails extends AppCompatActivity {

    private TextView _nomEmballage;
    private ImageView _imageEmballage;
    private ImageView _imagePoubelle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_sans_scan_categ_details);

        _nomEmballage = (TextView)findViewById(R.id.textView_nomEmballage);
        _imageEmballage = (ImageView) findViewById(R.id.imageView_imageEmballage);
        _imagePoubelle = (ImageView) findViewById(R.id.imageView_imagePoubelle);
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
                                break;
                case "Plastiques" :
                    _imageEmballage.setImageResource(R.drawable.plastique);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    break;
                case "Piles" :
                    _imageEmballage.setImageResource(R.drawable.batterie);
                    break;
                case "Fruits et Légumes" :
                    _imageEmballage.setImageResource(R.drawable.la_nourriture_saine);
                    break;
                case "Verdures" :
                    _imageEmballage.setImageResource(R.drawable.herbe);
                    break;
                case "Vaiselles" :
                    _imageEmballage.setImageResource(R.drawable.assiette);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_verte);
                    break;
                case "Médicament" :
                    _imageEmballage.setImageResource(R.drawable.medicament);
                    break;
                case "Cartons" :
                    _imageEmballage.setImageResource(R.drawable.papiercarton);
                    _imagePoubelle.setImageResource(R.drawable.poubelle_jaune);
                    break;
                case "Vêtements" :
                    _imageEmballage.setImageResource(R.drawable.vetements);
                    break;
                case "Meubles" :
                    _imageEmballage.setImageResource(R.drawable.meubles);
                    break;
                case "Electroménager" :
                    _imageEmballage.setImageResource(R.drawable.electromenager);
                    break;
            }
        }
    }
}