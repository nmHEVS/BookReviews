package com.example.bookreviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class ShowBook extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book);
    }

    /** Called when click on display Reviews*/
    public void displayAllReviews(View view) {
        Intent intent = new Intent(this, ShowAllReviews.class);
        startActivity(intent);
    }
}
