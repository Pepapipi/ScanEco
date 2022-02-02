package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProduitDetails extends AppCompatActivity {
    private ImageView _imageEmballage;
    private TextView _nomProduit;
    private TextView _marqueProduit;
    private String _codeBarre;
    private Produit _produitObtenu;
    private String _nomProduitRecupere;
    private String _marqueProduitRecupere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_details);
        _nomProduit = findViewById(R.id.textView_nomProduit);
        _imageEmballage = findViewById(R.id.imageView_EmballageScan);
        _marqueProduit = findViewById(R.id.textView_marqueProduit);
        afficherProduit();
    }

    protected void afficherProduit() {
        if (getIntent() != null) {
            try {
                _codeBarre = getIntent().getStringExtra("codebarre");
                _produitObtenu = Produit.getProductFromBarCode(_codeBarre);
                _nomProduitRecupere = _produitObtenu.getNom();
                _marqueProduitRecupere = _produitObtenu.getMarque();

                _produitObtenu.loadImage();
                _imageEmballage.setImageDrawable(_produitObtenu.getImage());

            } catch (Exception e) {
                _marqueProduitRecupere = null;
                _nomProduitRecupere = "Erreur, le produit n'est pas répertoriée dans la base de données OpenFoodFacts";

            }
            this._nomProduit.setText(_nomProduitRecupere);
            this._marqueProduit.setText(_marqueProduitRecupere);

        }
    }
}
