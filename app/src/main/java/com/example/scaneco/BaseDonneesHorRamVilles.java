package com.example.scaneco;

import android.os.AsyncTask;

import com.example.scaneco.horrampoubelles.AccueilHorRamPoubelles;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class BaseDonneesHorRamVilles extends AsyncTask<String, Void, String> {


    public AccueilHorRamPoubelles activity;
    public String json;
    @Override
    protected String doInBackground(String... urls) {
        String ret;
        try{
            URL url = new URL(urls[0]);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "ScanEco - Android - Version 0.1");
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
