package com.example.bookreviews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.bookreviews.R.layout.activity_display_book;

public class ShowBook extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_display_book);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
    }
}
