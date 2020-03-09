package com.example.bookreviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.bookreviews.R.layout.activity_display_review;



public class ShowReview extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_display_review);

    }


    /** Called when click on update review*/
    public void updateReview(View view) {

        //todo : Récupérer les infos de la review pour préremplir les champs

        Intent intent = new Intent(this, AddReview.class);
        startActivity(intent);
    }

}
