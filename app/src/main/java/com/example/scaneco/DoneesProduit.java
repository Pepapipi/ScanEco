package com.example.scaneco;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoneesProduit {
    private final List<String> listeRecyclable= new ArrayList<>();
    private final String[] tabRecyclable = {"Bouteille plastique", "Etui en carton", "Brique en carton", "Canette","Bouteille en PET",
            "Bouteille en plastique", "plastic bottle","Bouteille et bouchon 100% recyclable", "Boite en métal"
            ,"Bouchon en plastique","Couvercle en métal", "Carton", "Opercule en papier", "Opercule en métal", "Sachet en papier", "Etiquette"};

    private final List<String> listeVerre = new ArrayList<>();
    private final String[] tabVerre = {"Verres", "Verre", "Bouteille en verre", "Bouteille verre","Pot en verre"};

    private final List<String> listeNonRecyclable = new ArrayList<>();
    private final String[] tabNonRecyclabe = {"Sachet en plastique", "Film en plastique", "Sachet plastique", "Plastique", "Barquette en plastique",
            "Pot en plastique", "Couvercle en plastique", "Capsules métalliques", "Bouchons en liège", "Opercule"};

    private String text1="";
    private String text2="";
    private String text3="";
    public void initialisationDesListes()
    {
        listeRecyclable.addAll(Arrays.asList(tabRecyclable));
        listeNonRecyclable.addAll(Arrays.asList(tabNonRecyclabe));
        listeVerre.addAll(Arrays.asList(tabVerre));
    }

    public void afficherPoubelleSansTexte(Produit pdt, ImageView img1, ImageView img2, ImageView img3)
    {
        try{
        setText1("");
        setText2("");
        setText3("");
        img1.setImageResource(0);
        img2.setImageResource(0);
        img3.setImageResource(0);

        //Affiche les poubelles correspondantes, tant que il n'y a pas de text
        //Ce qui veut dire qu'aucun emballage a était trouvé..
        int i=0;
        while(i<pdt.emball.length && getText1().isEmpty())
        {
            setText1(affichageCorrectImage(pdt,i, getText1(),img1));

            i++;
        }
        while(i<pdt.emball.length && getText2().isEmpty())
        {
            setText2(affichageCorrectImage(pdt, i, getText2(),img2));
            i++;
        }
        while(i<pdt.emball.length && getText3().isEmpty())
        {
            setText3(affichageCorrectImage(pdt,i, getText3(),img3));
            i++;
        }}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Fonction qui permet regarde dans les 3 listes si l'emballage qu'on a dans le tableau
     * _produitObtenu.emball
     * @param i TODO
     * @param text TODO
     * @param image TODO
     * @return TODO
     */
    private String affichageCorrectImage(Produit pdt, int i,String text, ImageView image)
    {
        String emballage = upperCaseFirst(pdt.emball[i].replace(" fr:","").replace(" 100% recyclable",""));
        if (listeRecyclable.contains(emballage))
        {
            text=emballage;

            image.setImageResource(R.drawable.poubelle_jaune);

        }
        else if (listeNonRecyclable.contains(emballage))
        {
            text=emballage;
            image.setImageResource(R.drawable.poubelle_noire);

        }
        else if(listeVerre.contains(emballage))
        {
            text=emballage;
            image.setImageResource(R.drawable.poubelle_verte);

        }
        return text;
    }

    public void recupererLeTextePourEnvoyerAuxDetails(Produit pdt)
    {
        try{
            //Affiche les poubelles correspondantes, tant que il n'y a pas de text
            //Ce qui veut dire qu'aucun emballage a était trouvé..
            int i=0;
            while(i<pdt.emball.length && getText1().isEmpty())
            {
                setText1(retourneTexte(pdt,i, getText1()));
                i++;
            }
            while(i<pdt.emball.length && getText2().isEmpty())
            {
                setText2(retourneTexte(pdt, i, getText2()));
                i++;
            }
            while(i<pdt.emball.length && getText3().isEmpty())
            {
                setText3(retourneTexte(pdt,i, getText3()));
                i++;
            }}
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private String retourneTexte(Produit pdt, int i, String text)
    {
        String emballage = upperCaseFirst(pdt.emball[i].replace(" fr:","").replace(" 100% recyclable",""));
        if (listeRecyclable.contains(emballage) || listeNonRecyclable.contains(emballage) || listeVerre.contains(emballage))
            text=emballage;
        return text;
    }



    private static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public void affichageCardViewAvecTexteEtImage(String txtRecu, TextView textV, ImageView img, CardView cardv)
    {
        if (listeRecyclable.contains(txtRecu))
        {
            textV.setText(txtRecu);
            img.setImageResource(R.drawable.poubelle_jaune);
            cardv.setVisibility(View.VISIBLE);

        }
        else if (listeNonRecyclable.contains(txtRecu))
        {
            textV.setText(txtRecu);
            img.setImageResource(R.drawable.poubelle_noire);
            cardv.setVisibility(View.VISIBLE);

        }
        else if(listeVerre.contains(txtRecu))
        {
            textV.setText(txtRecu);
            img.setImageResource(R.drawable.poubelle_verte);
            cardv.setVisibility(View.VISIBLE);

        }
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }
}
