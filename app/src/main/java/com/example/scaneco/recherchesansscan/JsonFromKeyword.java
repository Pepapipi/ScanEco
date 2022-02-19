package com.example.scaneco.recherchesansscan;

import android.os.AsyncTask;

import com.example.scaneco.MainActivity;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class JsonFromKeyword extends AsyncTask<String, Void, String> {
    public Exception e;
    public String json;
    public AccueilRechercheSansScan activity;

    @Override
    protected String doInBackground(String... strings) {
        String ret = null;
        try {
            String keywords = strings[0];
            String page = strings[1];

            keywords = keywords.replaceAll(" ", "+");

            URL url = new URL("https://fr.openfoodfacts.org/cgi/search.pl?action=process&search_terms=" + keywords + "&sort_by=unique_scans_n&page_size=24&page="+page+"&json=1");

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "ScanEco - Android - Version 0.1");
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            ret = scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            this.e = e;
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String s) {
        json = s;
        if (activity != null) {
            activity.jsonGot(s);
        }
    }
}