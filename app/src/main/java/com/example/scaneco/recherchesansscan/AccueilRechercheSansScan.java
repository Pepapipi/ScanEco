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

import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.scaneco.MainActivity;
import com.example.scaneco.Produit;
import com.example.scaneco.ProduitDetails;
import com.example.scaneco.R;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilRechercheSansScan extends AppCompatActivity {

    private ImageButton _boutonRetourScan;
    private GridLayout _gridLayout;
    ArrayList<String> arrayEmballage = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProgramAdapter adapter;
    JsonFromKeyword jsonFromKeyword;
    List<Produit> produits;
    RecyclerViewClickListner recyclerViewClickListner;
    ScrollView scrlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_recherche_sans_scan);

        _gridLayout = (GridLayout)findViewById(R.id.gridViewRechercheSansScan);
        setSingleEvent(_gridLayout);
        //Barre de navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.accueilHorRamPoubelles:
                    ouvrirHorRamPoubelles();
                    break;

                case R.id.accueilAnimations:
                    ouvrirAnimations();
                    break;

            }
            return true;
        });
        //Bouton de retour
        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ouvrirLeScan();
            }
        });
        setOnClickListner();




    }

    protected void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void ouvrirHorRamPoubelles()
    {
        Intent intent = new Intent(this, AccueilHorRamPoubelles.class);
        startActivity(intent);
    }
    public void ouvrirAnimations()
    {
        Intent intent = new Intent(this, AccueilAnimations.class);
        startActivity(intent);
    }

    protected void setSingleEvent (GridLayout mainGrid){
        arrayEmballage.addAll(Arrays.asList(getResources().getStringArray(R.array.mes_dechets)));

        for (int i = 0; i< mainGrid.getChildCount(); i++){
            CardView _cardView = (CardView) mainGrid.getChildAt(i);
            final int _finalI = i;
            _cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(AccueilRechercheSansScan.this, RechercheSansScanCategDetails.class);
                intent.putExtra("info", arrayEmballage.get(_finalI));
                startActivity(intent);
                }
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initRecyclerView() {
        scrlView = findViewById(R.id.scrollView);
        scrlView.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.recy);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramAdapter(produits,recyclerViewClickListner);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void rechercheDuProduit(String s)
    {
        jsonFromKeyword = new JsonFromKeyword();
        jsonFromKeyword.activity=this;
        jsonFromKeyword.execute(s);
    }

    public void jsonGot(String json){
        TextView txt = findViewById(R.id.text);
        try {
            produits = Produit.getProductsListFromJson(json);
        }
        catch (Exception e){
            txt.setText(e.toString());
        }
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recherche_sans_scan,menu);
        MenuItem item = menu.findItem(R.id.search_product);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(AccueilRechercheSansScan.this, s, Toast.LENGTH_SHORT).show();

                rechercheDuProduit(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setOnClickListner(){
        recyclerViewClickListner = new RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                int i=0;
                String text1="";
                String text2="";
                String text3="";
                while(i<produits.get(position).emball.length && text1.isEmpty())
                {
                    text1 = adapter.verificationNomDeEmballage(position,i,text1);
                    i++;
                }
                while(i<produits.get(position).emball.length && text2.isEmpty())
                {
                    text2 = adapter.verificationNomDeEmballage(position,i,text2);
                    i++;
                }
                while(i<produits.get(position).emball.length && text3.isEmpty())
                {
                    text3 = adapter.verificationNomDeEmballage(position,i,text3);
                    i++;
                }


                Intent intent = new Intent(getApplicationContext(), ProduitDetails.class);
                intent.putExtra("nomPdt", produits.get(position).getNom());
                intent.putExtra("marquePdt", produits.get(position).getMarque());
                intent.putExtra("codeBarre", produits.get(position).getCode());
                intent.putExtra("text1",text1);
                intent.putExtra("text2",text2);
                intent.putExtra("text3", text3);
                startActivity(intent);
            }
        };
    }
}