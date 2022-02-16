package com.example.scaneco.horRamPoubelles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.animations.AccueilAnimations;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class AccueilHorRamPoubelles extends AppCompatActivity {

    ListView recherche_ville;
    ArrayAdapter<String> adapter;
    private ImageButton _boutonRetourScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_hor_ram_poubelles);

        recherche_ville= (ListView) findViewById(R.id.recherche_ville);

        ArrayList<String> arrayVille = new ArrayList<>();
        arrayVille.addAll(Arrays.asList(getResources().getStringArray(R.array.mes_villes)));

        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ouvrirLeScan();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){

                case R.id.accueilAnimations:
                    ouvrirAnimations();
                    break;
            }
            return true;
        });

        adapter = new ArrayAdapter<String>(AccueilHorRamPoubelles.this, android.R.layout.simple_list_item_1, arrayVille);
        recherche_ville.setAdapter(adapter);

        recherche_ville.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View view, int position, long id){

                Toast.makeText(AccueilHorRamPoubelles.this,"Selectionné "+recherche_ville.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AccueilHorRamPoubelles.this, HorRamPoubellesDetailsVille.class);

                String nomVille= recherche_ville.getItemAtPosition(position).toString();
                intent.putExtra("valRecup",nomVille);

                startActivity(intent);
            }
        });

    }

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


    public void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void ouvrirAnimations()
    {
        Intent intent = new Intent(this, AccueilAnimations.class);
        finish();
        startActivity(intent);
    }



}