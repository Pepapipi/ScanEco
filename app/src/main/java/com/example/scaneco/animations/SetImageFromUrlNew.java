//package com.example.scaneco.animations;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.widget.ImageView;
//
//import java.io.InputStream;
//import java.net.URL;
//import java.util.concurrent.Callable;
//
//public class SetImageFromUrlNew implements Callable<Bitmap> {
//    private final String input;
//    private final ImageView imageView;
//    private final Exception e;
//
//    public SetImageFromUrl(ImageView imageView, String url){
//        this.imageView = imageView;
//        this.input = url;
//    }
//
//    public Bitmap call(){
//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
//        }
//        catch (Exception exception){
//            this.e = exception;
//        }
//        return bitmap;
//    }
//}