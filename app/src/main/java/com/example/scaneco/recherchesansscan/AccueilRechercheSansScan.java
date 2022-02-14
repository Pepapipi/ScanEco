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
import android.widget.ImageView;
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
        listeRecyclable.addAll(Arrays.asList(tabRecyclable));
        listeNonRecyclable.addAll(Arrays.asList(tabNonRecyclabe));
        listeVerre.addAll(Arrays.asList(tabVerre));
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

    /**
     *
     * @param menu
     * @return boolean
     * Quand l'utilisateur clique sur la loupe et écrit le nom du produit
     * Il faut voir si c'est un code-barres
     */
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
                /*
                    On cherche à savoir si un code barres est saisie
                    Il faut savoir que un code barre est composé de 8 ou 13 chiffres
                 */
                try {
                    long nombreRecup = Long.parseLong(s);
                    if (s.length()==8||s.length()==13)
                    {
                        //Il faut maintenant envoyé les données à la page produit.
                        Produit _produitAEnvoyer = Produit.getProductFromBarCode(s);

                        int i = 0;
                        String text1 = "";
                        String text2 = "";
                        String text3 = "";
                        while (i < _produitAEnvoyer.emball.length && text1.isEmpty()) {
                            text1 = affichageCorrect(_produitAEnvoyer,i,text1);
                            i++;
                        }
                        while (i < _produitAEnvoyer.emball.length && text2.isEmpty()) {
                            text2 = affichageCorrect(_produitAEnvoyer,i,text2);
                            i++;
                        }
                        while (i < _produitAEnvoyer.emball.length && text3.isEmpty()) {
                            text3 = affichageCorrect(_produitAEnvoyer,i,text3);
                            i++;
                        }
                        Intent intent = new Intent(getApplicationContext(), ProduitDetails.class);
                        intent.putExtra("nomPdt", _produitAEnvoyer.getNom());
                        intent.putExtra("marquePdt", _produitAEnvoyer.getMarque());
                        intent.putExtra("codeBarre", s);
                        intent.putExtra("text1", text1);
                        intent.putExtra("text2", text2);
                        intent.putExtra("text3", text3);

                        startActivity(intent);
                        Toast.makeText(AccueilRechercheSansScan.this,"Yes codeBrre Ok", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(AccueilRechercheSansScan.this,"Malheuresement le code barre est faux", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(AccueilRechercheSansScan.this,"On lance la recherche", Toast.LENGTH_SHORT).show();
                    rechercheDuProduit(s);
                }
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
    private void setOnClickListner(){
        recyclerViewClickListner = new RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                try {

                    int i = 0;
                    String text1 = "";
                    String text2 = "";
                    String text3 = "";
                    while (i < produits.get(position).emball.length && text1.isEmpty()) {
                        text1 = adapter.verificationNomDeEmballage(position, i, text1);
                        i++;
                    }
                    while (i < produits.get(position).emball.length && text2.isEmpty()) {
                        text2 = adapter.verificationNomDeEmballage(position, i, text2);
                        i++;
                    }
                    while (i < produits.get(position).emball.length && text3.isEmpty()) {
                        text3 = adapter.verificationNomDeEmballage(position, i, text3);
                        i++;
                    }


                    Intent intent = new Intent(getApplicationContext(), ProduitDetails.class);
                    intent.putExtra("nomPdt", produits.get(position).getNom());
                    intent.putExtra("marquePdt", produits.get(position).getMarque());
                    intent.putExtra("codeBarre", produits.get(position).getCode());

                    intent.putExtra("text1", text1);
                    intent.putExtra("text2", text2);
                    intent.putExtra("text3", text3);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(AccueilRechercheSansScan.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }

        };
    }

    public String affichageCorrect(Produit _produitObtenu, int i,String text)
    {
        String _emballage = upperCaseFirst(_produitObtenu.emball[i].replaceAll(" fr:","").replaceAll(" 100% recyclable",""));
        if (listeRecyclable.contains(_emballage))
        {
            text=_emballage;

        }
        else if (listeNonRecyclable.contains(_emballage))
        {
            text=_emballage;

        }
        else if(listeVerre.contains(_emballage))
        {
            text=_emballage;
        }
        return text;
    }

    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }
}