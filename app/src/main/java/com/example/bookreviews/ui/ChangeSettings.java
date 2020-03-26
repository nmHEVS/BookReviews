package com.example.bookreviews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.bookreviews.R;

public class ChangeSettings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_change_settings);
    }


    /** Called when click on the dark/light mode */
    public void changeTheme(View v) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        finish();
        startActivity(new Intent(ChangeSettings.this, ChangeSettings.this.getClass()));
    }

    /** Called when click on Apply settings : */
    public void applyMode(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /** Called when click on information*/
    public void displayAbout(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }


}
