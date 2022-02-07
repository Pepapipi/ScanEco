package com.example.scaneco;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.example.scaneco.recherchesansscan.AccueilRechercheSansScan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextView _nomProduit;
    private TextView _ecranBlanc;
    private TextView _marqueProduit;

    private CodeScanner _mCodeScanner;
    private CodeScannerView _mCodeScannerView;
    private ImageButton _boutonRechercheSansScan;

    private ImageView _imageEmballage;

    private GestureDetector _gestureUtilisateur;
    private float y1,y2;
    private static int MIN_DISTANCE = 150;

    private Produit _produitObtenu;
    private String _codeBarre;
    private String _nomProduitRecupere;
    private String _marqueProduitRecupere;

    private String text1;
    private String text2;
    private String text3;
    private ImageView _imageViewPoubelle1;
    private ImageView _imageViewPoubelle2;
    private ImageView _imageViewPoubelle3;


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
        setContentView(R.layout.activity_main);

        //Création et initialisation du scanner
        _mCodeScannerView = findViewById(R.id.scanner_view);
        _mCodeScanner = new CodeScanner(this, _mCodeScannerView);

        //Initialisation des variables qui nous permettront d'affocher les
        //caracteristiques du produit
        _ecranBlanc = findViewById(R.id.textView_ecranBlanc);
        _nomProduit = findViewById(R.id.textView_nomProduit);
        _imageEmballage = findViewById(R.id.imageView_EmballageScan);
        _marqueProduit = findViewById(R.id.textView_marqueProduit);

        _imageViewPoubelle1 = findViewById(R.id.imageView_poubelle1);
        _imageViewPoubelle2 = findViewById(R.id.imageView_poubelle2);
        _imageViewPoubelle3 = findViewById(R.id.imageView_poubelle3);
        //Initialisation des listes
        for (int i =0; i< tabRecyclable.length;i++){
            listeRecyclable.add(tabRecyclable[i]);
        }
        for (int i =0; i< tabNonRecyclabe.length;i++){
            listeNonRecyclable.add(tabNonRecyclabe[i]);
        }
        for (int i =0; i< tabVerre.length;i++){
            listeVerre.add(tabVerre[i]);
        }

        //Initialisation du swipe de l'utilisateur
        this._gestureUtilisateur = new GestureDetector(MainActivity.this,this);

        //Initialisation de la barre de menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.accueilHorRamPoubelles:
                    ouvrirHorRamPoubelles();
                    break;

                case R.id.accueilAnimations:
                    ouvrirAnimations();
                    break;
            }
            return true;
        });


        //Initialisation du bouton qui ouvre la page recherche sans scan
        _boutonRechercheSansScan = findViewById(R.id.bouton1);
        //Quand le bouton est cliqué alors il sera redirigé vers la page recherche sans scan
        _boutonRechercheSansScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirRechercheSansScan();
            }
        });


        //Pour se servir du scan, l'utilisateur doit autoriser l'accès à la caméra
        //On vérifie s'il a autorisé ou non l'accès à la caméra
        //S'il a refusé on redemande si on peut l'utilser, mais on ne peut pas se servir du scan
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 123);
        }
        //S'il accepte, on lance le scan
        else{
            startScanning();
        }
    }


    /**
     * Fonction qui permet de scanner des codes-barres
     * Le carré blanc apparait au premier scan
     * Attention ! Il le carré blanc restera tant que l'utilisateur n'a pas changé de page(appelé vue)
     */

    private void startScanning() {
        //Au départ quand aucun article n'est scanné le texte n'apparait pas
        //Donc on lui demande d'être invisible
        _ecranBlanc.setVisibility(View.INVISIBLE);
        _nomProduit.setVisibility(View.INVISIBLE);
        _marqueProduit.setVisibility(View.INVISIBLE);
        _mCodeScanner.startPreview();

        //Le scan décode un code-barres
        _mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //Code-barres scanné
                            _codeBarre = result.getText();
                            //Le produit rendu par OFF
                            _produitObtenu= Produit.getProductFromBarCode(_codeBarre);

                            //Nom produit
                            _nomProduitRecupere = _produitObtenu.getNom();
                            _marqueProduitRecupere = _produitObtenu.getMarque();

                            //Image emballage
                            _produitObtenu.loadImage();
                            _imageEmballage.setImageDrawable(_produitObtenu.getImage());

                            //Reinitialise les paramètres
                            text1="";
                            text2="";
                            text3="";
                            _imageViewPoubelle1.setImageResource(0);
                            _imageViewPoubelle2.setImageResource(0);
                            _imageViewPoubelle3.setImageResource(0);

                            //Affiche les poubelles correspondantes, tant que il n'y a pas de text
                            //Ce qui veut dire qu'aucun emballage a était trouvé..
                            int i=0;
                            while(i<_produitObtenu.emball.length && text1.isEmpty())
                            {
                                text1 = affichageCorrect(i,text1,_imageViewPoubelle1);
                                i++;
                            }
                            while(i<_produitObtenu.emball.length && text2.isEmpty())
                            {
                                text2 = affichageCorrect(i,text2,_imageViewPoubelle2);
                                i++;
                            }
                            while(i<_produitObtenu.emball.length && text3.isEmpty())
                            {
                                text3 = affichageCorrect(i,text3,_imageViewPoubelle3);
                                i++;
                            }


                        }
                        catch (Exception e){

                            _nomProduitRecupere =e.toString();
                            //_nomProduitRecupere = "Erreur, le produit n'est pas répertoriée dans la base de données OpenFoodFacts";
                        }
                        _nomProduit.setText(_nomProduitRecupere);
                        _marqueProduit.setText(_marqueProduitRecupere);
                        //On les affiche en rendant le texte visible
                        _ecranBlanc.setVisibility(View.VISIBLE);
                        _nomProduit.setVisibility(View.VISIBLE);
                        _marqueProduit.setVisibility(View.VISIBLE);
                        _mCodeScanner.startPreview();
                    }
                });
            }
        });

    }

    /**
     * Fonction qui vérifie si on a la permission d'acceder à la caméra
     * La fonction s'active au premier lancement de l'application
     * Elle s'active ensuite à chaque fois qu'on arrive sur le scan et qu'on n'a toujours pas la permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Un petit message lui indique que c'est bon, et on lance le scan
                Toast.makeText(this,"Permission accordée", Toast.LENGTH_SHORT).show();
                startScanning();
            }
            else{
                //Un message lui indique qu'il ne peut toujours pas se servir du scan
                Toast.makeText(this,"Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
    * Fonction permettant d'aller sur une autre page(vue)
    * Ici cette fonction permettra d'accéder à la page recherche sans scan
    **/
    public void ouvrirRechercheSansScan(){
        Intent intent = new Intent(this, AccueilRechercheSansScan.class);
        startActivity(intent);
    }

    public void ouvrirAnimations()
    {
        Intent intent = new Intent(this, AccueilAnimations.class);
        startActivity(intent);
    }

    public void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }
    public void ouvrirProduitDetail()
    {
        Intent intent = new Intent(this, ProduitDetails.class);
        intent.putExtra("nomPdt", _nomProduitRecupere);
        intent.putExtra("marquePdt", _marqueProduitRecupere);
        intent.putExtra("codeBarre", _codeBarre);
        intent.putExtra("text1",text1);
        intent.putExtra("text2",text2);
        intent.putExtra("text3", text3);
        startActivity(intent);
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
        String _emballage = upperCaseFirst(_produitObtenu.emball[i].replaceAll(" fr:","").replaceAll(" 100% recyclable",""));
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

    /**
     * Les fonctions ci-dessous sont nécessaires pour le swipe !!!
     * @param event c'est un MotionEvent, c'est un evenement qui est déclenché après
     *              une action qui bouge
     * @return event ça retourne l'event qui permet de savoir ce qu'a fait l'utilisateur
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        _gestureUtilisateur.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                y2 = event.getY();
                float valueY = y2-y1;
                if(Math.abs(valueY) > MIN_DISTANCE){
                    if(y2<y1) {
                        ouvrirProduitDetail();
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


}

