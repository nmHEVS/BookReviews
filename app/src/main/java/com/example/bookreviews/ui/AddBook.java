package com.example.bookreviews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookreviews.R;

import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.repository.BookRepository;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BookViewModel;

public class AddBook extends AppCompatActivity {

    private static final String TAG = "BookAdd";

    private BookEntity book;
    private BookViewModel viewModel;



    private EditText etTitle;
    private EditText etAuthor;
    private EditText etCategory;
    private EditText etEdition;
    private EditText etYearPublished;
    private EditText etPlotSummary;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        //display the activity with the toolbar
        setContentView(R.layout.activity_add_book);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //creating text view to display data
        etTitle = findViewById(R.id.title_editText);
        etAuthor = findViewById(R.id.author_editText);
        etCategory = findViewById(R.id.category_editText);
        etEdition = findViewById(R.id.edition_editText);
        etYearPublished = findViewById(R.id.yearPublished_editText);
        etPlotSummary = findViewById(R.id.plotSummary_editText);

        //creating the book object
        BookViewModel.Factory factory = new BookViewModel.Factory(getApplication(),"",
                "","","","",0,
                "");
        viewModel = ViewModelProviders.of(this, factory).get(BookViewModel.class);
    }

    /** Called when click on the ADD button*/
    public void add_book(View v){
        String title = etTitle.getText().toString();

        // check if the field title is not null (title is mandatory)
        if(title.matches("")){
            toast = Toast.makeText(this, "Title is mandatory", Toast.LENGTH_LONG);
            toast.show();
        }else{
            //create the book entity
            book = new BookEntity(etTitle.getText().toString(),etAuthor.getText().toString(),
                    etCategory.getText().toString(),etEdition.getText().toString(),
                    Integer.parseInt(etYearPublished.getText().toString()),
                    etPlotSummary.getText().toString());

            //create the book in the DB
            viewModel.createBook(book, new OnAsyncEventListener() {
                //write in the LOG if the book is created or not
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createBook: success");

                    onBackPressed();
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "CreateClient: failure", e);
                }
            });
        }
    }

    /** Called when click on the CANCEL button : end the current activity and go back to the last activity*/
    public void cancel(View v){
        this.finish();
    }
}
