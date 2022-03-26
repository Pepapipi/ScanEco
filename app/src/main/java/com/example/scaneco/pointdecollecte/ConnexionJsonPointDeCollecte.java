package com.example.scaneco.pointdecollecte;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ConnexionJsonPointDeCollecte extends AsyncTask<String, Void, String> {


    @SuppressLint("StaticFieldLeak")
    private final RecherchePointDeCollecte activity;

    public ConnexionJsonPointDeCollecte(RecherchePointDeCollecte activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        String ret;
        try{
            URL url = new URL(urls[0]);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "UtilisationOSM - Android - Version 0.1");
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            ret = scanner.useDelimiter("\\A").next();

        }catch (Exception e){
            ret = e.toString();
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.json(s);
    }
}
