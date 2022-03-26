package com.example.scaneco;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.horrampoubelles.AccueilHorRamPoubelles;
import com.example.scaneco.pointdecollecte.RecherchePointDeCollecte;
import com.example.scaneco.recherchesansscan.AccueilRechercheSansScan;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String USER_AGENT = "ScanEco - Android - Version 0.1";

    private DoneesProduit mDonneesDuProduit;
    private TextView mNomProduit;
    private View mEcranBlanc;
    private TextView mMarqueProduit;

    private View mTraitView;

    private CodeScanner mCodeScanner;

    private ImageView mImageEmballage;

    private GestureDetector mGestureUtilisateur;
    private float y1;

    private Produit mProduitObtenu;
    private String mCodeBarre;
    private String mNomProduitRecupere;
    private String mMarqueProduitRecupere;

    private ImageView mImageViewPoubelle1;
    private ImageView mImageViewPoubelle2;
    private ImageView mImageViewPoubelle3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Création et initialisation du scanner
        CodeScannerView mCodeScannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, mCodeScannerView);

        //Initialisation des variables qui nous permettront d'affocher les
        //caracteristiques du produit
        mEcranBlanc = findViewById(R.id.view_ecranBlanc);
        mNomProduit = findViewById(R.id.textView_nomProduit);
        mImageEmballage = findViewById(R.id.imageView_EmballageScan);
        mMarqueProduit = findViewById(R.id.textView_marqueProduit);

        mImageViewPoubelle1 = findViewById(R.id.imageView_poubelle1);
        mImageViewPoubelle2 = findViewById(R.id.imageView_poubelle2);
        mImageViewPoubelle3 = findViewById(R.id.imageView_poubelle3);

        mTraitView = findViewById(R.id.traitView);
        //Initialisation des listes
        mDonneesDuProduit = new DoneesProduit();
        mDonneesDuProduit.initialisationDesListes();

        //Initialisation du swipe de l'utilisateur
        this.mGestureUtilisateur = new GestureDetector(MainActivity.this,this);

        //Initialisation de la barre de menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            int itemId = item.getItemId();
            if (itemId == R.id.accueilHorRamPoubelles) {
                ouvrirHorRamPoubelles();
            } else if (itemId == R.id.accueilAnimations) {
                ouvrirAnimations();
            } else if (itemId == R.id.accueilPointDeCollecte) {
                ouvrirRecherchePointDeCollecte();
            }
            return true;
        });


        //Initialisation du bouton qui ouvre la page recherche sans scan
        ImageButton mBoutonRechercheSansScan = findViewById(R.id.bouton1);
        //Quand le bouton est cliqué alors il sera redirigé vers la page recherche sans scan
        mBoutonRechercheSansScan.setOnClickListener(v -> ouvrirRechercheSansScan());

        //Initialisation du bouton qui ouvre la page des réglages
        ImageButton mBoutonReglages = findViewById(R.id.boutonReglages);
        //Quand le bouton est cliqué alors il sera redirigé vers la page des réglages
        mBoutonReglages.setOnClickListener(v -> ouvrirReglages());


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
        mEcranBlanc.setVisibility(View.INVISIBLE);
        mNomProduit.setVisibility(View.INVISIBLE);
        mMarqueProduit.setVisibility(View.INVISIBLE);
        mTraitView.setVisibility(View.INVISIBLE);
        mCodeScanner.startPreview();

        //Le scan décode un code-barres
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {

            try {
                //Code-barres scanné
                mCodeBarre = result.getText();
                //Le produit rendu par OFF
                mProduitObtenu = Produit.getProductFromBarCode(mCodeBarre);

                //Nom produit
                mNomProduitRecupere = mProduitObtenu.getNom();
                mMarqueProduitRecupere = mProduitObtenu.getMarque();

                //Image emballage
                mProduitObtenu.loadImage();
                mImageEmballage.setImageDrawable(mProduitObtenu.getImage());

                mDonneesDuProduit.afficherPoubelleSansTexte(mProduitObtenu, mImageViewPoubelle1, mImageViewPoubelle2, mImageViewPoubelle3);
            }
            catch (InterruptedException interruptedException){
                Thread.currentThread().interrupt();
            }
            catch (Exception e){
                mNomProduitRecupere =e.toString();
            }
            mNomProduit.setText(mNomProduitRecupere);
            mMarqueProduit.setText(mMarqueProduitRecupere);
            //On les affiche en rendant le texte visible
            mEcranBlanc.setVisibility(View.VISIBLE);
            mNomProduit.setVisibility(View.VISIBLE);
            mMarqueProduit.setVisibility(View.VISIBLE);
            mTraitView.setVisibility(View.VISIBLE);
            mCodeScanner.startPreview();
        }));

    }

    /**
     * Fonction qui vérifie si on a la permission d'acceder à la caméra
     * La fonction s'active au premier lancement de l'application
     * Elle s'active ensuite à chaque fois qu'on arrive sur le scan et qu'on n'a toujours pas la permission
     */
    @Override
    //TODO à changer car Deprecated
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

    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder aux Animations
     **/
    public void ouvrirAnimations()
    {
        Intent intent = new Intent(this, AccueilAnimations.class);
        startActivity(intent);
    }

    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder à la page des horaires de ramassage des poubelles
     **/
    public void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }
    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder à la page des points de collecte
     **/
    protected void ouvrirRecherchePointDeCollecte()
    {
        Intent intent = new Intent(this, RecherchePointDeCollecte.class);
        startActivity(intent);
    }
    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder à la page des réglages
     **/
    public void ouvrirReglages()
    {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    /**
     * Fonction permettant d'aller sur une autre page(vue)
     * Ici cette fonction permettra d'accéder à la page des détails des produits lors du scan
     **/
    public void ouvrirProduitDetail()
    {
        Intent intent = new Intent(this, ProduitDetails.class);
        intent.putExtra("nomProduit", mNomProduitRecupere);
        intent.putExtra("marqueProduit", mMarqueProduitRecupere);
        intent.putExtra("nomEmballage1", mDonneesDuProduit.getText1());
        intent.putExtra("nomEmballage2", mDonneesDuProduit.getText2());
        intent.putExtra("nomEmballage3", mDonneesDuProduit.getText3());
        intent.putExtra("marqueProduit", mMarqueProduitRecupere);
        intent.putExtra("codeBarre", mCodeBarre);
        startActivity(intent);
    }


    /**
     * Les fonctions ci-dessous sont nécessaires pour le swipe !!!
     * @param event c'est un MotionEvent, c'est un evenement qui est déclenché après
     *              une action qui bouge
     * @return event ça retourne l'event qui permet de savoir ce qu'a fait l'utilisateur
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureUtilisateur.onTouchEvent(event);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            y1 = event.getY();
        } else if (action == MotionEvent.ACTION_UP) {
            float y2 = event.getY();
            float valueY = y2 - y1;
            final int MIN_DISTANCE = 150;
            if (Math.abs(valueY) > MIN_DISTANCE && y2 < y1) {
                ouvrirProduitDetail();
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
        //Nothing because not needed
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
        //Nothing because not needed
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


}

