package com.example.bookreviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowAllBooks extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_books);


        //Show list of all books


    }

    /** Called when click on the book to display*/
    public void displayBook(View view) {
        Intent intent = new Intent(this, ShowBook.class);
        startActivity(intent);
    }
}
