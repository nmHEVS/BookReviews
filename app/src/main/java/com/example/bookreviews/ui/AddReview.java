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
import com.example.bookreviews.database.dao.BookDao;
import com.example.bookreviews.database.dao.ReviewDao;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BookViewModel;
import com.example.bookreviews.viewmodel.ReviewViewModel;

public class AddReview extends AppCompatActivity {


    private static final String TAG = "ReviewAdd";

    private ReviewEntity review;
    private ReviewViewModel viewModel;

    private ReviewDao reviewDao;

    private EditText etBook;
    private EditText etAuthor;
    private EditText etGrade;
    private EditText etDate;
    private EditText etReview;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_add_review);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);



        etBook = findViewById(R.id.book_editText);
        etAuthor = findViewById(R.id.author_editText);
        etGrade = findViewById(R.id.grade_editText);
        etDate = findViewById(R.id.date_editText);
        etReview = findViewById(R.id.reviewText_editText);

        ReviewViewModel.Factory factory = new ReviewViewModel.Factory(getApplication(),0,
                0,"","",0,"");
        //viewModel = ViewModelProviders.of(this, factory).get(BookViewModel.class);
        //cancel


    }


    public void add_review(View v){

        Long bookId = getIntent().getLongExtra("bookId", 0L);

        review = new ReviewEntity(bookId,Double.parseDouble(etGrade.getText().toString()),
                    etAuthor.getText().toString(), etDate.getText().toString(),
                    etReview.getText().toString());

        viewModel.createReview(review, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createReview: success");

                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "CreateClient: failure", e);
            }
        });

    }

    public void cancel(View v){
        this.finish();
    }
}

