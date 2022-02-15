package com.example.scaneco;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoneesProduit {
    private List<String> listeRecyclable= new ArrayList<String>();
    private final String[] tabRecyclable = {"Bouteille plastique", "Etui en carton", "Brique en carton", "Canette","Bouteille en PET",
            "Bouteille en plastique", "plastic bottle","Bouteille et bouchon 100% recyclable", "Boite en métal"
            ,"Bouchon en plastique","Couvercle en métal", "Carton", "Opercule papier", "Pot en plastique", "Couvercle en plastique"};

    private List<String> listeVerre = new ArrayList<String>();
    private final String[] tabVerre = {"Verres", "Verre", "Bouteille en verre", "Bouteille verre","Pot en verre"};

    private List<String> listeNonRecyclable = new ArrayList<String>();
    private final String[] tabNonRecyclabe = {"Sachet en plastique", "Film en plastique", "Sachet plastique", "Plastique", "Barquette en plastique"};

    public void initialisationDesListes()
    {
        listeRecyclable.addAll(Arrays.asList(tabRecyclable));
        listeNonRecyclable.addAll(Arrays.asList(tabNonRecyclabe));
        listeVerre.addAll(Arrays.asList(tabVerre));
    }

    public void afficherPoubelleSansTexte(Produit pdt, ImageView img1, ImageView img2, ImageView img3)
    {
        String text1="";
        String text2="";
        String text3="";
        img1.setImageResource(0);
        img2.setImageResource(0);
        img3.setImageResource(0);

        //Affiche les poubelles correspondantes, tant que il n'y a pas de text
        //Ce qui veut dire qu'aucun emballage a était trouvé..
        int i=0;
        while(i<pdt.emball.length && text1.isEmpty())
        {
            text1 = affichageCorrect(pdt,i,text1,img1);
            i++;
        }
        while(i<pdt.emball.length && text2.isEmpty())
        {
            text2 = affichageCorrect(pdt, i,text2,img2);
            i++;
        }
        while(i<pdt.emball.length && text3.isEmpty())
        {
            text3 = affichageCorrect(pdt,i,text3,img3);
            i++;
        }
    }

    public void affichageCardViewAvecTexte(Produit pdt, TextView txtPasse1, TextView txtPasse2, TextView txtPasse3, ImageView img1, ImageView img2, ImageView img3, CardView card1, CardView card2, CardView card3)
    {
        String text1="";
        String text2="";
        String text3="";
        img1.setImageResource(0);
        img2.setImageResource(0);
        img3.setImageResource(0);
        int i=0;
        while(i<pdt.emball.length && text1.isEmpty())
        {
            text1 = affichageCardViewAvecTexteEtImage(pdt,i,txtPasse1,text1,img1, card1);
            i++;
        }
        while(i<pdt.emball.length && text2.isEmpty())
        {
            text2 = affichageCardViewAvecTexteEtImage(pdt,i,txtPasse2,text2,img2, card2);
            i++;
        }
        while(i<pdt.emball.length && text3.isEmpty())
        {
            text3 = affichageCardViewAvecTexteEtImage(pdt,i,txtPasse3,text3,img3, card3);
            i++;
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
    private String affichageCorrect(Produit pdt, int i,String text, ImageView image)
    {
        String _emballage = upperCaseFirst(pdt.emball[i].replaceAll(" fr:","").replaceAll(" 100% recyclable",""));
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



    private static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    private String affichageCardViewAvecTexteEtImage(Produit pdt, int i,TextView textV, String text, ImageView img, CardView cardv)
    {
        String _emballage = upperCaseFirst(pdt.emball[i].replaceAll(" fr:","").replaceAll(" 100% recyclable",""));
        if (listeRecyclable.contains(_emballage))
        {
            text=_emballage;
            textV.setText(text);
            img.setImageResource(R.drawable.poubelle_jaune);
            cardv.setVisibility(View.VISIBLE);

        }
        else if (listeNonRecyclable.contains(_emballage))
        {
            text=_emballage;
            textV.setText(text);
            img.setImageResource(R.drawable.poubelle_noire);
            cardv.setVisibility(View.VISIBLE);

        }
        else if(listeVerre.contains(_emballage))
        {
            text=_emballage;
            textV.setText(text);
            img.setImageResource(R.drawable.poubelle_verte);
            cardv.setVisibility(View.VISIBLE);

        }
        return text;
    }
}
