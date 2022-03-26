package com.example.scaneco;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // TODO à changer car deprecated
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();
        },1500);
    }
}