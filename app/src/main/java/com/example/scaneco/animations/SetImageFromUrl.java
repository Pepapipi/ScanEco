package com.example.scaneco.animations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class SetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    Exception e;
    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
        }
        catch (Exception e){
            this.e = e;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null){
            bmImage.setImageBitmap(bitmap);
        }
        else {
            Log.println(Log.ERROR, "Error", e.toString());
        }
    }

    public SetImageFromUrl(ImageView imageView, String url){
        this.bmImage = imageView;
        this.execute(url);
    }
}
