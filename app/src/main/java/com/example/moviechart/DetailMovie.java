package com.example.moviechart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DetailMovie extends AppCompatActivity {

    String title;
    String contents;
    String path;
    String genre;
    String runtime;
    String rating;
    String director;
    String actor[] = new String[200];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.detail_movie);

        getSupportActionBar().hide();

        Intent intent = getIntent();

        TextView textView_title_detail = (TextView)findViewById(R.id.TextView_title_detail);
        TextView textView_contents_detail = (TextView)findViewById(R.id.TextView_content_detail);
        ImageView imageView_title_detail = (ImageView)findViewById(R.id.ImageView_title_detail);
        TextView textView_contents_overdetail = (TextView)findViewById(R.id.TextView_content_overdetail);

        title = intent.getStringExtra("text_title");

        contents = intent.getStringExtra("text_content");

        path = intent.getStringExtra("path");

        genre = intent.getStringExtra("genre");
        runtime = intent.getStringExtra("runtime");
        rating = intent.getStringExtra("rating");
        director = intent.getStringExtra("director");
        actor = intent.getStringArrayExtra("actor");

        textView_title_detail.setText(title);


        String contents_detail_exceptactor = "\n\n장르 : " + genre + "\n관람가 : " + rating + "\n런타임 : " + runtime+ "분\n감독 : " + director+ "\n배우 : ";
        String contents_detail_actor = "";


        for(int i = 0; i < 10; i++){
            if(i == 0) contents_detail_actor = contents_detail_actor + actor[i];
            else{
                if(actor[i] != null){
                    contents_detail_actor = contents_detail_actor + ", " + actor[i];
                    if(i == 9 && actor[10] != null){
                        contents_detail_actor = contents_detail_actor + "...";
                    }
                }
            }
        }

        textView_contents_detail.setText(contents);

        textView_contents_overdetail.setText(contents_detail_exceptactor + "\n" + contents_detail_actor);

        try{
            Uri uri = Uri.parse(path);
            imageView_title_detail.setImageURI(uri);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}