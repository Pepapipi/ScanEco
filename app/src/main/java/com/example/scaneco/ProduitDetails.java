package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProduitDetails extends AppCompatActivity{
    private TextView m_nomEmballage1;
    private TextView m_nomEmballage2;
    private TextView m_nomEmballage3;

    private TextView m_nomProduit;
    private TextView m_marqueProduit;

    private ImageView m_imagePoubelle1;
    private ImageView m_imagePoubelle2;
    private ImageView m_imagePoubelle3;
    private ImageView m_imageProduit;

    private Produit m_produitObtenu;

    private CardView m_cardViewPoubelle1;
    private CardView m_cardViewPoubelle2;
    private CardView m_cardViewPoubelle3;

    private  DoneesProduit m_produitDonnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_details);

        m_nomEmballage1 = findViewById(R.id.textView);
        m_nomEmballage2 = findViewById(R.id.textView2);
        m_nomEmballage3 = findViewById(R.id.textView3);
        m_nomProduit = findViewById(R.id.textView_nomProduit);
        m_marqueProduit = findViewById(R.id.textView_marqueProduit);
        m_cardViewPoubelle1 = findViewById(R.id.firstDescription);
        m_cardViewPoubelle2 = findViewById(R.id.secondDescription);
        m_cardViewPoubelle3 = findViewById(R.id.thirdDescription);
        m_imagePoubelle1 = findViewById(R.id.imageView_poubelle1);
        m_imagePoubelle2 = findViewById(R.id.imageView_poubelle2);
        m_imagePoubelle3 = findViewById(R.id.imageView_poubelle3);
        m_imageProduit = findViewById(R.id.imageView_EmballageScan);

        m_produitDonnee = new DoneesProduit();
        m_produitDonnee.initialisationDesListes();


        try {
            String _codeBarre = getIntent().getStringExtra("codeBarre");
            m_produitObtenu = Produit.getProductFromBarCode(_codeBarre);
            m_nomProduit.setText(m_produitObtenu.getNom());
            m_marqueProduit.setText(m_produitObtenu.getMarque());
            m_produitObtenu.loadImage();
            m_imageProduit.setImageDrawable(m_produitObtenu.getImage());
            m_produitDonnee.affichageCardViewAvecTexte(m_produitObtenu,m_nomEmballage1,m_nomEmballage2,m_nomEmballage3,m_imagePoubelle1,m_imagePoubelle2,m_imagePoubelle3,m_cardViewPoubelle1,m_cardViewPoubelle2,m_cardViewPoubelle3);
        }
        catch (Exception ignored) {}

    }
}