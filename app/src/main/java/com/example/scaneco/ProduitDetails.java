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

        listeRecyclable.addAll(Arrays.asList(tabRecyclable));
        listeNonRecyclable.addAll(Arrays.asList(tabNonRecyclabe));
        listeVerre.addAll(Arrays.asList(tabVerre));

        m_nomEmballage1.setText(getIntent().getStringExtra("text1"));
        m_nomEmballage2.setText(getIntent().getStringExtra("text2"));
        m_nomEmballage3.setText(getIntent().getStringExtra("text3"));
        m_nomProduit.setText(getIntent().getStringExtra("nomPdt"));
        m_marqueProduit.setText(getIntent().getStringExtra("marquePdt"));

        imagePoubelle(getIntent().getStringExtra("text1"), m_imagePoubelle1, m_cardViewPoubelle1);
        imagePoubelle(getIntent().getStringExtra("text2"), m_imagePoubelle2, m_cardViewPoubelle2);
        imagePoubelle(getIntent().getStringExtra("text3"), m_imagePoubelle3, m_cardViewPoubelle3);

        try {
            String _codeBarre = getIntent().getStringExtra("codeBarre");
            m_produitObtenu = Produit.getProductFromBarCode(_codeBarre);
            m_produitObtenu.loadImage();
            m_imageProduit.setImageDrawable(m_produitObtenu.getImage());
        }
        catch (Exception ignored)
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

    /**
     * Fonction qui permet regarde dans les 3 listes si l'emballage qu'on a dans le tableau
     * _produitObtenu.emball
     * @param i
     * @param text
     * @param image
     * @return
     */
    public String affichageCorrect(int i,String text, ImageView image)
    {
        String _emballage = upperCaseFirst(m_produitObtenu.emball[i].replaceAll(" fr:","").replaceAll(" 100% recyclable",""));
        if (listeRecyclable.contains(_emballage))
        {
            text=_emballage;
            image.setImageResource(R.drawable.poubelle_jaune);

        }
        else if (listeNonRecyclable.contains(_emballage))
        {
            text=_emballage;
            image.setImageResource(R.drawable.poubelle_noire);

        }
        else if(listeVerre.contains(_emballage))
        {
            text=_emballage;
            image.setImageResource(R.drawable.poubelle_verte);

        }
        return text;
    }

    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }
}