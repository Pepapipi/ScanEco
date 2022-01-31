package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RechercheSansScanCategDetails extends AppCompatActivity {

    private TextView _nomEmballage;
    private ImageView _imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_sans_scan_categ_details);

        _nomEmballage = (TextView)findViewById(R.id.textView_nomEmballage);
        _imageView = (ImageView) findViewById(R.id.imageView_imageEmballage);
        if(getIntent() != null){
            String info = getIntent().getStringExtra("info");

            _nomEmballage.setText(info);
            switch(info)
            {
                case "Papiers" : _imageView.setImageResource(R.drawable.papier);
                                break;
                case "Verres" :_imageView.setImageResource(R.drawable.duvin);
                                break;
                case "Plastiques" :_imageView.setImageResource(R.drawable.plastique);
                    break;
                case "Piles" :_imageView.setImageResource(R.drawable.batterie);
                    break;
                case "Fruits et Légumes" :_imageView.setImageResource(R.drawable.la_nourriture_saine);
                    break;
                case "Verdures" :_imageView.setImageResource(R.drawable.herbe);
                    break;
                case "Vaiselles" :_imageView.setImageResource(R.drawable.assiette);
                    break;
                case "Médicament" :_imageView.setImageResource(R.drawable.medicament);
                    break;
                case "Cartons" :_imageView.setImageResource(R.drawable.papiercarton);
                    break;
                case "Vêtements" :_imageView.setImageResource(R.drawable.vetements);
                    break;
                case "Meubles" :_imageView.setImageResource(R.drawable.meubles);
                    break;
                case "Electroménager" :_imageView.setImageResource(R.drawable.electromenager);
                    break;
            }
        }
    }
}