package com.example.scaneco.horRamPoubelles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scaneco.BaseDonneesHorRamVilles;
import com.example.scaneco.MainActivity;
import com.example.scaneco.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HorRamPoubellesDetailsVille extends AppCompatActivity {

    private ImageButton _boutonRetourScan;


    ArrayAdapter<String> adapter;
    ArrayList<String> arrayVille;
    ListView recherche_ville;

    static Ville villeRecuperee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hor_ram_poubelles_details_ville);

        TextView textViewJoursSelectifs = (TextView) findViewById(R.id.joursSelectifs);
        TextView textViewJoursMenagers = (TextView) findViewById(R.id.joursMenagers);
        TextView textViewHeuresSelectifs = (TextView) findViewById(R.id.heuresSelectifs);
        TextView textViewHeuresMenagers = (TextView) findViewById(R.id.heuresMenagers);

        TextView textView;
        ImageView imageView;
        Bitmap bImage;


        textView = findViewById(R.id.NomDeLaVille);


        String nomVilleRecuperee = villeRecuperee.nom;
        textView.setText(nomVilleRecuperee);


        if (villeRecuperee.codePostal.equals("64600")||
                villeRecuperee.codePostal.equals("64200")||
                villeRecuperee.codePostal.equals("64100")||
                villeRecuperee.codePostal.equals("64340")||villeRecuperee.codePostal.equals("64210"))
        {
            bImage= BitmapFactory.decodeResource(this.getResources(), R.drawable.cartographie_bayonne);
            imageView = findViewById(R.id.cartographie);
            imageView.setImageBitmap(bImage);
        }

        if (villeRecuperee.codePostal.equals("64990"))
        {
            bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.saint_pierre_dirube);
            imageView = findViewById(R.id.cartographie);
            imageView.setImageBitmap(bImage);
        }


        textViewJoursSelectifs.setText(villeRecuperee.joursSelectifs);
        textViewJoursMenagers.setText(villeRecuperee.joursMenagers);
        textViewHeuresSelectifs.setText(villeRecuperee.heuresSelectifs);
        textViewHeuresMenagers.setText(villeRecuperee.heuresMenagers);


    }
}







/*

    Bundle b = getIntent().getExtras();
    //VilleList maliste = getIntent().getExtras().get("nomDeLaListe");
    Ville ville= (Ville) getIntent().getExtras().get("nomDeLaListe");

        for (int i = 0; i < maliste.size(); i++) {
            arrayVille.add(maliste.get(i).nom);
        }


    recherche_ville = (ListView) findViewById(R.id.recherche_ville);


    adapter = new ArrayAdapter<String>(
    HorRamPoubellesDetailsVille.this,
    android.R.layout.simple_list_item_1,
    arrayVille
        );
        recherche_ville.setAdapter(adapter);

        recherche_ville.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(HorRamPoubellesDetailsVille.this, "Selectionné " + recherche_ville.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            //villeSelectionnee=new Ville();
            //villeSelectionnee=listVille.get(position).get(0);

            Intent intent;
            //intent.putExtra("nombreDeVilleDansLaListe",nombreDeVille);

            String nomDeLaListe = recherche_ville.getItemAtPosition(position).toString();

        }
    });

}


    ///////////////Loupe de recherche\\\\\\\\\\\\\\\
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hor_ram_poubelles, menu);
        MenuItem item = menu.findItem(R.id.search_ville);
        SearchView searchView = (SearchView) item.getActionView();

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
        /////////////////Navigation\\\\\\\\\\\\\\\\\\\\

    /*
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.accueilHorRamPoubelles:
                    ouvrirHorRamPoubelles();
                    break;

            }
            return true;
        });

        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ouvrirLeScan();
            }
        });
    }








    public void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }*/


