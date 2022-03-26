package com.example.scaneco.horrampoubelles;

import androidx.annotation.NonNull;
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

import com.example.scaneco.R;

import java.util.ArrayList;
import java.util.List;

public class VillesDansLaListe extends AppCompatActivity {

    ImageButton boutonRetourAccueilHorRamPoubelles;
    ListView rechercheVille;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayVille =new ArrayList<>();


    static List<Ville> maListeDePlusiersVilles;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villes_dans_la_liste);

        ///////////////Navigation\\\\\\\\\\\\\\\
        boutonRetourAccueilHorRamPoubelles = findViewById(R.id.boutonRetourAccueilHorRamPoubelles);
        boutonRetourAccueilHorRamPoubelles.setOnClickListener(v -> ouvrirHorRamPoubelles());



        for(int i=1;i<maListeDePlusiersVilles.size();i++)
        {
            arrayVille.add(maListeDePlusiersVilles.get(i).getNom());
        }

        rechercheVille = findViewById(R.id.list_villes);

        adapter = new ArrayAdapter<>(
                VillesDansLaListe.this,
                android.R.layout.simple_list_item_1,
                arrayVille
        );
        rechercheVille.setAdapter(adapter);

        rechercheVille.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(VillesDansLaListe.this, HorRamPoubellesDetailsVille.class);
            setVilleDansHorRam(maListeDePlusiersVilles, position+1);
            startActivity(intent);
        });

    }

    private static void setVilleDansHorRam(@NonNull List<Ville> listeDeListeDeVilles, int position){
        HorRamPoubellesDetailsVille.villeRecuperee = listeDeListeDeVilles.get(position);
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
    ///////////////Loupe de recherche\\\\\\\\\\\\\\\



    /////////////////Navigation\\\\\\\\\\\\\\\\\\\\
    public void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }

}