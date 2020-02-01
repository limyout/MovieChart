package com.example.moviechart;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class LoadingMovie extends AppCompatActivity {
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loading_movie);
        getSupportActionBar().hide();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(LoadingMovie.this, MainActivity.class));
                startActivity(new Intent(LoadingMovie.this, MovieActivitySearch.class));
                finish();
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}