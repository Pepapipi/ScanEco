package com.example.scaneco.horRamPoubelles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.scaneco.BaseDonneesHorRamVilles;
import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.SplashScreen;
import com.example.scaneco.animations.AccueilAnimations;

import com.example.scaneco.recherchesansscan.AccueilRechercheSansScan;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccueilHorRamPoubelles extends AppCompatActivity {

    ///////////////Variables\\\\\\\\\\\\\\\
    ListView recherche_ville;
    ArrayAdapter<String> adapter;
    private ImageButton _boutonRetourScan;
    String fichierJson;
    static List<List<Ville>> listeDeListeDeVilles;
    Intent intent;

    BaseDonneesHorRamVilles baseDonneesHorRamVilles;
    ArrayList<String> arrayVille= new ArrayList<>();;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_hor_ram_poubelles);

        //BARRE DE NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.accueilAnimations:
                    ouvrirAnimations();
                    break;
            }
            return true;
        });
        //Bouton de retour
        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());

        baseDonneesHorRamVilles = new BaseDonneesHorRamVilles();
        baseDonneesHorRamVilles.activity = this;

        ///////////////Recuperation de la BD en JSON\\\\\\\\\\\\\\\
        try {
            baseDonneesHorRamVilles.execute("https://api.npoint.io/d3f9c37f03c56013060c");
        }
        catch (Exception e) {}

    }

    public void json(String fichierJson) {
        try {
            listeDeListeDeVilles = FichierJsonManager.valeurRenvoyeeJson(fichierJson);

        } catch (Exception e) {
            listeDeListeDeVilles = null;
        }
        int tableauCompteurVille[]=new int[listeDeListeDeVilles.size()];




        ///////////////Ajout à mon tableau de tout les noms de mes villes et du code postal \\\\\\\\\\\\\\\
        for (int i = 0; i < listeDeListeDeVilles.size(); i++) {
            arrayVille.add(listeDeListeDeVilles.get(i).get(0).nom +" / "+listeDeListeDeVilles.get(i).get(0).codePostal);
            tableauCompteurVille[i]=listeDeListeDeVilles.get(i).size();
        }



        ///////////////Affichage des villes dans la liste \\\\\\\\\\\\\\\
        recherche_ville = (ListView) findViewById(R.id.list_villes);

        adapter = new ArrayAdapter<String>(
                AccueilHorRamPoubelles.this,
                android.R.layout.simple_list_item_1,
                arrayVille
        );
        recherche_ville.setAdapter(adapter);

        recherche_ville.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View view, int position, long id){


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
            }
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void ouvrirAnimations()
    {
        Intent intent = new Intent(this, AccueilAnimations.class);
        startActivity(intent);
    }





}
