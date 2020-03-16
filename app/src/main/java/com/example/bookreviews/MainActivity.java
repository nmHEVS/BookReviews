package com.example.bookreviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, ChangeSettings.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
