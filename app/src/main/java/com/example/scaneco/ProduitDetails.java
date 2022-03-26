package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProduitDetails extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_details);

        TextView mNomEmballage1 = findViewById(R.id.textView);
        TextView mNomEmballage2 = findViewById(R.id.textView2);
        TextView mNomEmballage3 = findViewById(R.id.textView3);
        TextView mNomProduit = findViewById(R.id.textView_nomProduit);
        TextView mMarqueProduit = findViewById(R.id.textView_marqueProduit);
        CardView mCardViewPoubelle1 = findViewById(R.id.firstDescription);
        CardView mCardViewPoubelle2 = findViewById(R.id.secondDescription);
        CardView mCardViewPoubelle3 = findViewById(R.id.thirdDescription);
        ImageView mImagePoubelle1 = findViewById(R.id.imageView_poubelle1);
        ImageView mImagePoubelle2 = findViewById(R.id.imageView_poubelle2);
        ImageView mImagePoubelle3 = findViewById(R.id.imageView_poubelle3);
        ImageView mImageProduit = findViewById(R.id.imageView_EmballageScan);

        DoneesProduit mProduitDonnee = new DoneesProduit();
        mProduitDonnee.initialisationDesListes();


        try {
            String codeBarre = getIntent().getStringExtra("codeBarre");
            Produit mProduitObtenu = Produit.getProductFromBarCode(codeBarre);
            mNomProduit.setText(getIntent().getStringExtra("nomProduit"));
            mMarqueProduit.setText(getIntent().getStringExtra("marqueProduit"));
            String nomEmballageRecup1 = getIntent().getStringExtra("nomEmballage1");
            String nomEmballageRecup2 = getIntent().getStringExtra("nomEmballage2");
            String nomEmballageRecup3 = getIntent().getStringExtra("nomEmballage3");
            mProduitObtenu.loadImage();
            mImageProduit.setImageDrawable(mProduitObtenu.getImage());
            mProduitDonnee.affichageCardViewAvecTexteEtImage(nomEmballageRecup1, mNomEmballage1, mImagePoubelle1, mCardViewPoubelle1);
            mProduitDonnee.affichageCardViewAvecTexteEtImage(nomEmballageRecup2, mNomEmballage2, mImagePoubelle2, mCardViewPoubelle2);
            mProduitDonnee.affichageCardViewAvecTexteEtImage(nomEmballageRecup3, mNomEmballage3, mImagePoubelle3, mCardViewPoubelle3);

        }
        catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}