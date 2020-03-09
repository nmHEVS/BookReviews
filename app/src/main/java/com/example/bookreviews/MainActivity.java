package com.example.bookreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
    }


    /** Called when click on Show all Books*/
    public void displayAllBooks(View view) {
        Intent intent = new Intent(this, ShowAllBooks.class);
        startActivity(intent);
    }

    /** Called when click on Search a Book*/
    public void searchBook(View view) {
        Intent intent = new Intent(this, SearchBook.class);
        startActivity(intent);
    }

    /** Called when click on Search a Book*/
    public void addBook(View view) {
        Intent intent = new Intent(this, AddBook.class);
        startActivity(intent);
    }


}
