package com.example.scaneco.pointdecollecte;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;


import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.TaskRunner;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.horrampoubelles.AccueilHorRamPoubelles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class RecherchePointDeCollecte extends AppCompatActivity implements LocationListener {

    private MapView osm;
    private MapController mc;
    private CheckBox mcheck1;
    private CheckBox mcheck2;
    private CheckBox mcheck3;
    private CheckBox mcheck4;
    private CheckBox mcheck5
            ;
    List<PointDeCollecte> listePointsDeCollecte;
    Marker markerPosition;
    ArrayList<Marker> listeMarkerPoubelleNoire = new ArrayList<>();
    ArrayList<Marker> listeMarkerPoubelleJaune = new ArrayList<>();
    ArrayList<Marker> listeMarkerPoubelleVerte = new ArrayList<>();
    ArrayList<Marker> listeMarkerPoubelleBleue = new ArrayList<>();
    ArrayList<Marker> listeMarkerDechetterie = new ArrayList<>();
    TaskRunner taskRunner = new TaskRunner();

    private final ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (Boolean.TRUE.equals(result)){
                    this.recreate();
                    Log.e("TAG", "onActivityResult: PERMISSION GRANTED");
                } else {
                    Log.e("TAG", "onActivityResult: PERMISSION DENIED");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recherche_point_de_collecte);

        mcheck1 = findViewById(R.id.check1);
        mcheck2 = findViewById(R.id.check2);
        mcheck3 = findViewById(R.id.check3);
        mcheck4 = findViewById(R.id.check4);
        mcheck5 = findViewById(R.id.check5);
        //onde mostra a imagem do mapa
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setOsm(findViewById(R.id.mapView));
        getOsm().setTileSource(TileSourceFactory.MAPNIK);
        getOsm().setMultiTouchControls(true);

        MapListener mapListener = new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                Log.i("Script", "onScroll()");
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                Log.i("Script", "onZoom()");
                return false;
            }
        };
        mc = (MapController) getOsm().getController();
        markerPosition = new Marker(getOsm());
        GeoPoint pointFrance = new GeoPoint(43.48333, -1.48333);
        mc.setCenter(pointFrance);
        mc.setZoom(10);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            mPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION);

        }
        else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }

        getOsm().addMapListener(mapListener);


        ///////////////Recuperation de la BD en JSON\\\\\\\\\\\\\\\
        try {
            taskRunner.executeAsync(new ConnexionJsonPointDeCollecte("https://api.npoint.io/6696673b4c1fdcfcff8e", MainActivity.USER_AGENT), this::json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialisation de la barre de menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            int itemId = item.getItemId();
            if (itemId == R.id.accueilHorRamPoubelles) {
                ouvrirHorRamPoubelles();
            } else if (itemId == R.id.accueilAnimations) {
                ouvrirAnimations();
            }
            return true;
        });
        //Bouton de retour
        ImageButton boutonRetourScan = findViewById(R.id.boutonRetourScan);
        boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());

    }

    @Override
    public void onResume() {
        getOsm().onResume();
        super.onResume();

    }


    @Override
    public void onPause() {
        getOsm().onPause();
        super.onPause();
    }



    @Override
    public void onLocationChanged(Location location) {
        getOsm().getOverlays().remove(markerPosition);
        GeoPoint point = new GeoPoint(location.getLatitude(),location.getLongitude());
        markerPosition.setPosition(point);
        getOsm().getOverlays().add(markerPosition);

    }
    @Override
    public void onProviderEnabled(String provider) {
        //Nothing because not needed
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Nothing because not needed
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view) {
        // Check which checkbox was clicked
        int id = view.getId();
        switch (id) {
            case R.id.check1:
                ajouterBoutons(view, listeMarkerPoubelleNoire);
                break;
            case R.id.check2:
                ajouterBoutons(view, listeMarkerPoubelleJaune);
                break;
            case R.id.check3:
                ajouterBoutons(view, listeMarkerPoubelleVerte);
                break;
            case R.id.check4:
                ajouterBoutons(view, listeMarkerPoubelleBleue);
                break;
            case R.id.check5:
                ajouterBoutons(view, listeMarkerDechetterie);
                break;
            default:
                break;
        }
    }
    public void ajouterBoutons(View view, List<Marker> liste)
    {
        boolean checked = ((CheckBox) view).isChecked();
        int id = view.getId();
        for (Marker pdt : liste) {
            if(checked)
            {
                getOsm().getOverlays().add(pdt);
                switch (id) {
                    case R.id.check1:
                        mcheck1.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_sac_poub_500));
                        break;
                    case R.id.check2:
                        mcheck2.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_plastique_500));
                        break;
                    case R.id.check3:
                        mcheck3.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_verre_500));
                        break;
                    case R.id.check4:
                        mcheck4.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_papier_515));
                        break;
                    case R.id.check5:
                        mcheck5.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_dechetterie_500));
                        break;
                    }
                }


            else
            {
                getOsm().getOverlays().remove(pdt);
                switch (id) {
                    case R.id.check1:
                        mcheck1.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_sac_poub_500_false));
                        break;
                    case R.id.check2:
                        mcheck2.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_plastique_500_false));
                        break;
                    case R.id.check3:
                        mcheck3.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_verre_500_false));
                        break;
                    case R.id.check4:
                        mcheck4.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_papier_515_false));
                        break;
                    case R.id.check5:
                        mcheck5.setBackground(AppCompatResources.getDrawable(this, R.drawable.btn_dechetterie_500_false));
                        break;
                }
            }
        }
    }

    public void json(String fichierJson) {
        try {
            listePointsDeCollecte = JsonPointDeCollecte.valeurRenvoyeeJson(fichierJson);

        } catch (Exception e) {
            listePointsDeCollecte = new ArrayList<>();
        }
        for (int i = 0; i < listePointsDeCollecte.size(); i++) {
            Marker m = new Marker(getOsm());
            GeoPoint coordonnes = new GeoPoint(listePointsDeCollecte.get(i).getLongitude(), listePointsDeCollecte.get(i).getLatitude());
            m.setPosition(coordonnes);
            m.setTitle(listePointsDeCollecte.get(i).getAdresse() +"-"+ listePointsDeCollecte.get(i).getVille());
            if ("Noire".equals(listePointsDeCollecte.get(i).getType())) {
                m.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_ping_noire));
                listeMarkerPoubelleNoire.add(m);
            } else if ("Jaune".equals(listePointsDeCollecte.get(i).getType())) {
                m.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_ping_jaune));
                listeMarkerPoubelleJaune.add(m);
            } else if ("Verte".equals(listePointsDeCollecte.get(i).getType())) {
                m.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_ping_verre));
                listeMarkerPoubelleVerte.add(m);
            } else if ("Bleue".equals(listePointsDeCollecte.get(i).getType())) {
                m.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_ping_bleue));
                listeMarkerPoubelleBleue.add(m);
            } else if ("Dechetterie".equals(listePointsDeCollecte.get(i).getType())) {
                m.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_ping_dechetterie));
                listeMarkerDechetterie.add(m);
            }
        }}

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

    protected void ouvrirLeScan()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public MapView getOsm() {
        return osm;
    }

    public void setOsm(MapView osm) {
        this.osm = osm;
    }

}
