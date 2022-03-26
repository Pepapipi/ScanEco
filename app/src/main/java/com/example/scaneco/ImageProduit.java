package com.example.scaneco;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

public class ImageProduit implements Callable<Drawable> {
    private final String input;
    private final String userAgent;

    public ImageProduit(String input, String userAgent) {
        this.input = input;
        this.userAgent = userAgent;
    }

    @Override
    public Drawable call() throws IOException {
        URLConnection urlConnection = new URL(input).openConnection();
        urlConnection.setRequestProperty("User-Agent", userAgent);
        return Drawable.createFromStream(urlConnection.getInputStream(), "imageProduit");
    }
}
