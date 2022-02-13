package com.example.scaneco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProduitDetails extends AppCompatActivity{
private TextView text1D;
private TextView text2D;
private TextView text3D;
private TextView _nomProduit;
private TextView _marqueProduit;
private ImageView imageView1D;
private ImageView imageView2D;
private ImageView imageView3D;
private ImageView imageView_Emballage;
private Produit _produitObtenu;
private CardView _cardView1;
private CardView _cardView2;
private CardView _cardView3;



private List<String> listeRecyclable= new ArrayList<String>();
private final String[] tabRecyclable = {"Bouteille plastique", "Etui en carton", "Brique en carton", "Canette","Bouteille en PET",
            "Bouteille en plastique", "plastic bottle","Bouteille et bouchon 100% recyclable", "Boite en métal"
            ,"Bouchon en plastique","Couvercle en métal", "Carton", "Opercule papier", "Pot en plastique", "Couvercle en plastique"};

private List<String> listeVerre = new ArrayList<String>();
private final String[] tabVerre = {"Verres", "Verre", "Bouteille en verre", "Bouteille verre","Pot en verre"};

private List<String> listeNonRecyclable = new ArrayList<String>();
private final String[] tabNonRecyclabe = {"Sachet en plastique", "Film en plastique", "Sachet plastique", "Plastique", "Barquette en plastique"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_details);

            text1D = findViewById(R.id.textView);
            text2D = findViewById(R.id.textView2);
            text3D = findViewById(R.id.textView3);
            _nomProduit = findViewById(R.id.textView_nomProduit);
            _marqueProduit = findViewById(R.id.textView_marqueProduit);
            _cardView1 = findViewById(R.id.firstDescription);
            _cardView2 = findViewById(R.id.secondDescription);
            _cardView3 = findViewById(R.id.thirdDescription);

            imageView1D = findViewById(R.id.imageView_poubelle1);
            imageView2D = findViewById(R.id.imageView_poubelle2);
            imageView3D = findViewById(R.id.imageView_poubelle3);
            imageView_Emballage = findViewById(R.id.imageView_EmballageScan);

        listeRecyclable.addAll(Arrays.asList(tabRecyclable));
        listeNonRecyclable.addAll(Arrays.asList(tabNonRecyclabe));
        listeVerre.addAll(Arrays.asList(tabVerre));

            text1D.setText(getIntent().getStringExtra("text1"));
            text2D.setText(getIntent().getStringExtra("text2"));
            text3D.setText(getIntent().getStringExtra("text3"));

            _nomProduit.setText(getIntent().getStringExtra("nomPdt"));
            _marqueProduit.setText(getIntent().getStringExtra("marquePdt"));
            imagePoubelle(getIntent().getStringExtra("text1"), imageView1D,_cardView1);
            imagePoubelle(getIntent().getStringExtra("text2"), imageView2D,_cardView2);
            imagePoubelle(getIntent().getStringExtra("text3"), imageView3D, _cardView3);
            try{
                String _codeBarre = getIntent().getStringExtra("codeBarre");
                _produitObtenu = Produit.getProductFromBarCode(_codeBarre);
                _produitObtenu.loadImage();
                imageView_Emballage.setImageDrawable(_produitObtenu.getImage());
            }
            catch (Exception e)
            {

            }

    }


    private void imagePoubelle (String text, ImageView imgView, CardView cardView)
    {

        if (listeRecyclable.contains(text))
        {
            imgView.setImageResource(R.drawable.poubelle_jaune);
            cardView.setVisibility(View.VISIBLE);
        }
        else if(listeNonRecyclable.contains(text))
        {
            imgView.setImageResource(R.drawable.poubelle_noire);
            cardView.setVisibility(View.VISIBLE);
        }
        else if (listeVerre.contains(text))
        {
            imgView.setImageResource(R.drawable.poubelle_verte);
            cardView.setVisibility(View.VISIBLE);
        }
    }
}
