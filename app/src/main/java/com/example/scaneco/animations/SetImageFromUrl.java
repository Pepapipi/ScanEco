package com.example.scaneco.animations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;

class SetImageFromUrl implements Callable<Bitmap>{
    private final String url;

    public SetImageFromUrl(String url){
        this.url = url;
    }

    @Override
    public Bitmap call() throws IOException {
        return BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
    }
}