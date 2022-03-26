package com.example.scaneco.horrampoubelles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.scaneco.BaseDonneesHorRamVilles;
import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.animations.AccueilAnimations;

import com.example.scaneco.pointdecollecte.RecherchePointDeCollecte;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AccueilHorRamPoubelles extends AppCompatActivity {

    ///////////////Variables\\\\\\\\\\\\\\\
    ListView rechercheVille;
    ArrayAdapter<String> adapter;
    List<List<Ville>> listeDeListeDeVilles;
    Intent intent;

    BaseDonneesHorRamVilles baseDonneesHorRamVilles;
    ArrayList<String> arrayVille= new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_hor_ram_poubelles);

        //BARRE DE NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.accueilAnimations) {
                ouvrirAnimations();
            } else if (itemId == R.id.accueilPointDeCollecte) {
                ouvrirRecherchePointDeCollecte();
            }
            return true;
        });
        //Bouton de retour
        ImageButton boutonRetourScan = findViewById(R.id.boutonRetourScan);
        boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());

        baseDonneesHorRamVilles = new BaseDonneesHorRamVilles(this);

        ///////////////Recuperation de la BD en JSON\\\\\\\\\\\\\\\
        try {
            baseDonneesHorRamVilles.execute("https://api.npoint.io/d3f9c37f03c56013060c");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void json(String fichierJson) {
        int[] tableauCompteurVille;
        try {
            listeDeListeDeVilles = FichierJsonManager.valeurRenvoyeeJson(fichierJson);

        } catch (Exception e) {
            listeDeListeDeVilles = new ArrayList<>();
        }
        tableauCompteurVille = new int[listeDeListeDeVilles.size()];


        ///////////////Ajout à mon tableau de tout les noms de mes villes et du code postal \\\\\\\\\\\\\\\
        for (int i = 0; i < listeDeListeDeVilles.size(); i++) {
            arrayVille.add(listeDeListeDeVilles.get(i).get(0).nom +" / "+listeDeListeDeVilles.get(i).get(0).codePostal);
            tableauCompteurVille[i]=listeDeListeDeVilles.get(i).size();
        }



        ///////////////Affichage des villes dans la liste \\\\\\\\\\\\\\\
        rechercheVille = findViewById(R.id.list_villes);

        adapter = new ArrayAdapter<>(
                AccueilHorRamPoubelles.this,
                android.R.layout.simple_list_item_1,
                arrayVille
        );
        rechercheVille.setAdapter(adapter);

        rechercheVille.setOnItemClickListener((parent, view, position, id) -> {


            int nombreDeVilleDansLaListe=tableauCompteurVille[position];

            //Si on a plus d'une ville dans la liste on va ouvrir un page intermédiaire avec un autre choix
            if(nombreDeVilleDansLaListe>1)
            {
                intent = new Intent(AccueilHorRamPoubelles.this, VillesDansLaListe.class);
                VillesDansLaListe.maListeDePlusiersVilles=listeDeListeDeVilles.get(position);
            }
            //Sinon on ouvre le détail des horaires directement
            else
            {
                intent = new Intent(AccueilHorRamPoubelles.this, HorRamPoubellesDetailsVille.class);
                HorRamPoubellesDetailsVille.villeRecuperee=listeDeListeDeVilles.get(position).get(0);
            }

            startActivity(intent);
        });

    }




    ///////////////Loupe de recherche\\\\\\\\\\\\\\\
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hor_ram_poubelles,menu);
        MenuItem item = menu.findItem(R.id.search_ville);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    protected void ouvrirLeScan()
    {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void ouvrirAnimations()
    {
        intent = new Intent(this, AccueilAnimations.class);
        startActivity(intent);
    }
    protected void ouvrirRecherchePointDeCollecte()
    {
        intent = new Intent(this, RecherchePointDeCollecte.class);
        startActivity(intent);
    }





}
