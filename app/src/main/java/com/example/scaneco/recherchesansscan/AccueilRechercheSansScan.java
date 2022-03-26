package com.example.scaneco.recherchesansscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.scaneco.DoneesProduit;
import com.example.scaneco.MainActivity;
import com.example.scaneco.Produit;
import com.example.scaneco.ProduitDetails;
import com.example.scaneco.R;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.horrampoubelles.AccueilHorRamPoubelles;
import com.example.scaneco.pointdecollecte.RecherchePointDeCollecte;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilRechercheSansScan extends AppCompatActivity {

    ArrayList<String> arrayEmballage = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProgramAdapter adapter;
    JsonFromKeyword jsonFromKeyword;
    List<Produit> produits;
    RecyclerViewClickListner recyclerViewClickListner;
    ScrollView scrlView;
    DoneesProduit doneesProduit;
    LinearLayout layoutBoutons;
    String saisieRecup;
    Integer page;
    Button boutonPrecedent;
    Button boutonSuivant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_recherche_sans_scan);
        GridLayout gridLayout = findViewById(R.id.gridViewRechercheSansScan);
        setSingleEvent(gridLayout);
        //Barre de navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        //Bouton de retour
        ImageButton boutonRetourScan = findViewById(R.id.boutonRetourScan);
        boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());

        setOnClickListner();

    }

    protected void ouvrirLeScan() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    protected void ouvrirHorRamPoubelles() {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        finish();
        startActivity(intent);
    }

    public void ouvrirAnimations() {
        Intent intent = new Intent(this, AccueilAnimations.class);
        finish();
        startActivity(intent);
    }
    protected void ouvrirRecherchePointDeCollecte()
    {
        Intent intent = new Intent(this, RecherchePointDeCollecte.class);
        startActivity(intent);
    }

    protected void setSingleEvent(GridLayout mainGrid) {

        arrayEmballage.addAll(Arrays.asList(getResources().getStringArray(R.array.mes_dechets)));

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int _finalI = i;
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(AccueilRechercheSansScan.this, RechercheSansScanCategDetails.class);
                intent.putExtra("info", arrayEmballage.get(_finalI));
                startActivity(intent);
            });
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void initRecyclerView() {
        scrlView = findViewById(R.id.scrollView);
        scrlView.setVisibility(View.INVISIBLE);
        layoutBoutons = findViewById(R.id.layoutDesBoutons);
        layoutBoutons.setVisibility(View.VISIBLE);
        boutonPrecedent = findViewById(R.id.boutonPagePrecedente);
        boutonSuivant = findViewById(R.id.boutonPageSuivante);
        if(page == 1)
        {
            boutonPrecedent.setVisibility(View.INVISIBLE);
        }
        else
        {
            boutonPrecedent.setVisibility(View.VISIBLE);
            boutonPrecedent.setOnClickListener(v -> {
                Toast.makeText(getApplicationContext(),"Passage page précedente",Toast.LENGTH_SHORT).show();
                page--;
                rechercheDuProduit(saisieRecup,page.toString());
            });
        }
        if (produits.size() != 24)
        {
            boutonSuivant.setVisibility(View.INVISIBLE);
        }
        else
            {
                boutonSuivant.setVisibility(View.VISIBLE);
                boutonSuivant.setOnClickListener(v -> {
                    Toast.makeText(getApplicationContext(),"Passage page suivante",Toast.LENGTH_SHORT).show();
                    page++;
                    rechercheDuProduit(saisieRecup,page.toString());
                });
        }
        recyclerView = findViewById(R.id.recy);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramAdapter(produits, recyclerViewClickListner);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void rechercheDuProduit(String s, String page) {
        jsonFromKeyword = new JsonFromKeyword(this);
        //TODO deprecated async task
        jsonFromKeyword.execute(s, page);
    }

    public void jsonGot(String json) {
        TextView txt = findViewById(R.id.text);
        try {
            produits = Produit.getProductsListFromJson(json);
        } catch (Exception e) {
            txt.setText(e.toString());
        }
        initRecyclerView();
    }

    /**
     * @param menu ToDO ajouter description
     * @return boolean
     * Quand l'utilisateur clique sur la loupe et écrit le nom du produit
     * Il faut voir si c'est un code-barres
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recherche_sans_scan, menu);

        MenuItem item = menu.findItem(R.id.search_product);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                /*
                    On cherche à savoir si un code barres est saisie
                    Il faut savoir que un code barre est composé de 8 ou 13 chiffres
                 */
                try {
                    if (s.length() == 8 || s.length() == 13) {
                        doneesProduit = new DoneesProduit();
                        //Il faut maintenant envoyer les données à la page produit.
                        Produit produitAEnvoyer = Produit.getProductFromBarCode(s);
                        Intent intent = new Intent(getApplicationContext(), ProduitDetails.class);
                        doneesProduit.initialisationDesListes();
                        doneesProduit.recupererLeTextePourEnvoyerAuxDetails(produitAEnvoyer);
                        intent.putExtra("nomProduit", produitAEnvoyer.getNom());
                        intent.putExtra("marqueProduit", produitAEnvoyer.getMarque());
                        intent.putExtra("nomEmballage1", doneesProduit.getText1());
                        intent.putExtra("nomEmballage2", doneesProduit.getText2());
                        intent.putExtra("nomEmballage3", doneesProduit.getText3());
                        intent.putExtra("codeBarre", produitAEnvoyer.getCode());
                        startActivity(intent);
                        Toast.makeText(AccueilRechercheSansScan.this, "Yes codeBrre Ok", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AccueilRechercheSansScan.this, "Malheuresement le code barre est faux", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    saisieRecup = s;
                    page =1;
                    Toast.makeText(AccueilRechercheSansScan.this, "On lance la recherche", Toast.LENGTH_SHORT).show();
                    rechercheDuProduit(s,page.toString());
                    Thread.currentThread().interrupt();
                }

                //Cache le clavier après que l'utilisateur ait validé sa saisie
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Quand on clique sur item il ira sur la page ProduitDetail
     * Si et seulement si il y a AU MOINS un emballage (même s'il n'est pas dans nos listes)
     * S'il n'a aucun emballage (on peut verifier ça sur OFF, si y'a aucun conditionnement)
     * alors il ne pourra pas acceder à la page ProduitDetails sans faire crash l'application
     * Il aura un petit message d'erreur pour le moment, ensuite on le redirigera possiblement sur OFF
     * sur le produit qui n'a pas d'emballage
     */
    private void setOnClickListner() {

        recyclerViewClickListner = (v, position) -> {

            try {
                doneesProduit = new DoneesProduit();
                Intent intent = new Intent(getApplicationContext(), ProduitDetails.class);
                //A mofifier
                intent.putExtra("nomProduit", adapter.lpdt.get(position).getNom());
                intent.putExtra("marque", adapter.lpdt.get(position).getMarque());
                intent.putExtra("nomEmballage1", adapter.getListeEmballageChaqueProduit().get(position).get(0));
                intent.putExtra("nomEmballage2", adapter.getListeEmballageChaqueProduit().get(position).get(1));
                intent.putExtra("nomEmballage3", adapter.getListeEmballageChaqueProduit().get(position).get(2));
                intent.putExtra("codeBarre", produits.get(position).getCode());
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(AccueilRechercheSansScan.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }


}