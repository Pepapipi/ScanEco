package com.example.scaneco;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageProduit extends AsyncTask<String, Void, Drawable> {
    @Override
    protected Drawable doInBackground(String... urls) {
        Drawable ret;
        try{
            URL url = new URL(urls[0]);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "ScanEco - Android - Version 0.1");
            InputStream inputStream = connection.getInputStream();
            ret = Drawable.createFromStream(inputStream, "imageProduit");

        }catch (Exception e){
            ret = null;
        }
        return ret;
    }

}
