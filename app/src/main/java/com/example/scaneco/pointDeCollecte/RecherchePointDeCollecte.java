package com.example.scaneco.pointDeCollecte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.scaneco.MainActivity;
import com.example.scaneco.R;
import com.example.scaneco.animations.AccueilAnimations;
import com.example.scaneco.horRamPoubelles.AccueilHorRamPoubelles;
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

    private MapListener mapListener;
    public static MapView osm;
    private MapController mc;
    private LocationManager locationManager;
    static List<PointDeCollecte> listePointsDeCollecte;
    private static final int PERMISSAO_REQUERIDA = 1;
    Marker markerPosition;
    private ImageButton _boutonRetourScan;
    ArrayList<Marker> listeMarkerPoubelleNoire = new ArrayList<>();
    ArrayList<Marker> listeMarkerPoubelleJaune = new ArrayList<>();
    ArrayList<Marker> listeMarkerPoubelleVerte = new ArrayList<>();
    ArrayList<Marker> listeMarkerPoubelleBleue = new ArrayList<>();
    ArrayList<Marker> listeMarkerDechetterie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_point_de_collecte);

        //onde mostra a imagem do mapa
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        osm = (MapView) findViewById(R.id.mapView);
        osm.setTileSource(TileSourceFactory.MAPNIK);
        osm.setMultiTouchControls(true);

        mapListener = new MapListener() {
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
        mc = (MapController) osm.getController();
        markerPosition = new Marker(osm);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    1
            );
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        osm.addMapListener(mapListener);
        ///////////////Recuperation de la BD en JSON\\\\\\\\\\\\\\\
        ConnexionJsonPointDeCollecte baseDeDonneesPdtCollectes = new ConnexionJsonPointDeCollecte();
        baseDeDonneesPdtCollectes.activity = this;
        try {
            baseDeDonneesPdtCollectes.execute("https://api.npoint.io/6696673b4c1fdcfcff8e");
        } catch (Exception e) {
        }

        //Initialisation de la barre de menu
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
        //Bouton de retour
        _boutonRetourScan = findViewById(R.id.boutonRetourScan);
        _boutonRetourScan.setOnClickListener(v -> ouvrirLeScan());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSAO_REQUERIDA: {
                // Se a solicitação de permissão foi cancelada o array vem vazio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permissão cedida, recria a activity para carregar o mapa, só será executado uma vez
                    this.recreate();

                }

            }
        }
    }

    public void onResume() {
        osm.onResume();
        super.onResume();

    }


    public void onPause() {
        osm.onPause();
        super.onPause();
    }



    @Override
    public void onLocationChanged(Location location) {
        osm.getOverlays().remove(markerPosition);
        GeoPoint point = new GeoPoint(location.getLatitude(),location.getLongitude());
        markerPosition.setPosition(point);

        mc.setZoom(16);
        mc.animateTo(point);
        osm.getOverlays().add(markerPosition);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    int nbr=0;

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.check1:
                if(checked)
                {
                    for (Marker pdt:listeMarkerPoubelleNoire) {
                        osm.getOverlays().add(pdt);
                    }
                }
                else
                {
                    for (Marker pdt:listeMarkerPoubelleNoire) {
                        osm.getOverlays().remove(pdt);
                    }
                }
                break;
            case R.id.check2:
                if(checked)
                {
                    for (Marker pdt:listeMarkerPoubelleJaune) {
                        osm.getOverlays().add(pdt);
                    }
                }
                else
                {
                    for (Marker pdt:listeMarkerPoubelleJaune) {
                        osm.getOverlays().remove(pdt);
                    }
                }
                break;
            case R.id.check3:
                if(checked)
                {
                    for (Marker pdt:listeMarkerPoubelleVerte) {
                        osm.getOverlays().add(pdt);
                    }
                }
                else
                {
                    for (Marker pdt:listeMarkerPoubelleVerte) {
                        osm.getOverlays().remove(pdt);
                    }
                }
                break;
            case R.id.check4:
                if(checked)
                {
                    for (Marker pdt:listeMarkerPoubelleBleue) {
                        osm.getOverlays().add(pdt);
                    }
                }
                else
                {
                    for (Marker pdt:listeMarkerPoubelleBleue) {
                        osm.getOverlays().remove(pdt);
                    }
                }
                break;
            case R.id.check5:
                if(checked)
                {
                    for (Marker pdt:listeMarkerDechetterie) {
                        osm.getOverlays().add(pdt);
                    }
                }
                else
                {
                    for (Marker pdt:listeMarkerDechetterie) {
                        osm.getOverlays().remove(pdt);
                    }
                }
                break;
        }
    }

    public void json(String fichierJson) {
        try {
            listePointsDeCollecte = JsonPointDeCollecte.valeurRenvoyeeJson(fichierJson);

        } catch (Exception e) {
            listePointsDeCollecte = null;
        }
        for (int i = 0; i < listePointsDeCollecte.size(); i++) {
            Marker m = new Marker(osm);
            GeoPoint coordonnes = new GeoPoint(listePointsDeCollecte.get(i).getLongitude(), listePointsDeCollecte.get(i).getLatitude());
            m.setPosition(coordonnes);
            m.setTitle(listePointsDeCollecte.get(i).getAdresse() +"-"+ listePointsDeCollecte.get(0).getVille());
            switch (listePointsDeCollecte.get(i).getType())
            {
                case "Noire":
                    m.setIcon(getDrawable(R.drawable.ic_ping_noire));
                    listeMarkerPoubelleNoire.add(m);
                    break;
                case "Jaune":
                    m.setIcon(getDrawable(R.drawable.ic_ping_jaune));
                    listeMarkerPoubelleJaune.add(m);
                    break;
                case "Verte":
                    m.setIcon(getDrawable(R.drawable.ic_ping_verre));
                    listeMarkerPoubelleVerte.add(m);
                    break;
                case "Bleue":
                    m.setIcon(getDrawable(R.drawable.ic_ping_bleue));
                    listeMarkerPoubelleBleue.add(m);
                    break;
                case "Dechetterie":
                    m.setIcon(getDrawable(R.drawable.ic_ping_dechetterie));
                    listeMarkerDechetterie.add(m);
                    break;
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
}
