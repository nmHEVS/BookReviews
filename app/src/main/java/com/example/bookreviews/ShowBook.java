package com.example.bookreviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ShowBook extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_default_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, ChangeSettings.class);
                startActivity(intent);
                return true;
            case R.id.action_searchBooks:
                intent = new Intent(this, SearchBook.class);
                startActivity(intent);
                return true;
            case R.id.action_addBook:
                intent = new Intent(this, AddBook.class);
                startActivity(intent);
                return true;
            case R.id.action_allbooks:
                intent = new Intent(this, ShowAllBooks.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
