package com.example.scaneco;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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


public class MainActivity extends AppCompatActivity {

    private TextView _textView;
    private CodeScanner _mCodeScanner;
    private CodeScannerView _mCodeScannerView;
    private ImageButton _boutonRechercheSansScan;
    private Produit _produitRetourne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Création et initialisation du scanner
        _mCodeScannerView = findViewById(R.id.scanner_view);
        _mCodeScanner = new CodeScanner(this, _mCodeScannerView);

        //Texte qui nous sert à afficher ce que le scanner a récupérée
        _textView = findViewById(R.id.textView);


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




        //Création du bouton qui ouvre la page recherche sans scan
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


    /*Fonction qui permet de scanner des codes-barres
      Le carré blanc apparait au premier scan
      Attention ! Il le carré blanc restera tant que l'utilisateur n'a pas changé de page(appelé vue)
     */

    private void startScanning() {
        //Au départ quand aucun article n'est scanné le texte n'apparait pas
        //Donc on lui demande d'être invisible
        _textView.setVisibility(View.INVISIBLE);
        _mCodeScanner.startPreview();

        //Le scan décode un code-barres
        _mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //On récupère les chiffres scannés
                        String _nomProduit;
                        try {

                            _nomProduit = Produit.getProductFromBarCode(result.getText()).getNom();
                        }
                        catch (Exception e){
                            _nomProduit = "Erreur, le produit n'est pas répertoriée dans la base de données OpenFoodFacts";
                        }
                        _textView.setText(_nomProduit);
                        //On les affiche en rendant le texte visible
                        _textView.setVisibility(View.VISIBLE);

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

    /*Fonction permettant d'aller sur une autre page(vue)
    * Ici cette fonction permettra d'accéder à la page recherche sans scan
    * */
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





}