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

    /** Called when click on update book*/
    public void updateBook(View view) {

        //todo : Récupérer les infos du livre pour préremplir les champs

        Intent intent = new Intent(this, AddBook.class);
        startActivity(intent);
    }

    /** Called when click on display Reviews*/
    public void addReview(View view) {
        Intent intent = new Intent(this, AddReview.class);
        startActivity(intent);
    }
}
