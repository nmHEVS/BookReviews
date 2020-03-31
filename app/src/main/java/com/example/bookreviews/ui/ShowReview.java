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
import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BookViewModel;
import com.example.bookreviews.viewmodel.ReviewViewModel;


public class ShowReview extends AppCompatActivity {

    private static final String TAG = "ReviewsDetails";
    private ReviewViewModel viewModel;
    private ReviewEntity review;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etTitle;
    private EditText etAuthor;
    private EditText etDate;
    private EditText etGrade;
    private EditText etReview;

    private String bookTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_display_review);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);



        Long reviewId = getIntent().getLongExtra("reviewId", 0L);
        Long bookId = getIntent().getLongExtra("reviewId_book", 0L);
        bookTitle = getIntent().getStringExtra("reviewBookTitle");
        String reviewAuthor = getIntent().getStringExtra("reviewAuthor");
        double reviewGrade = getIntent().getDoubleExtra("reviewGrade", 0);
        String reviewDate = getIntent().getStringExtra("reviewDate");
        String reviewText = getIntent().getStringExtra("reviewReview");

        review = new ReviewEntity(bookId,reviewGrade,reviewAuthor,reviewDate,reviewText);
        review.setId(reviewId);

        initiateView();

        ReviewViewModel.Factory factory = new ReviewViewModel.Factory(getApplication(), reviewId,
                bookId,reviewAuthor,reviewDate,reviewGrade,reviewText);
        viewModel = ViewModelProviders.of(this, factory).get(ReviewViewModel.class);
        viewModel.getReview().observe(this, reviewEntity -> {
            if(reviewEntity != null){
                review = reviewEntity;

            }
        });
    }

    private void initiateView(){
        isEditable = false;
        etTitle = findViewById(R.id.book);
        etTitle.setText(bookTitle);
        etAuthor = findViewById(R.id.author);
        etAuthor.setText(review.getAuthor());
        etGrade = findViewById(R.id.grade);
        etGrade.setText(String.valueOf(review.getGrade()));
        etDate = findViewById(R.id.date);
        etDate.setText(review.getDate());
        etReview = findViewById(R.id.reviewText);
        etReview.setText(review.getReview());

        etTitle.setFocusable(false);
        etAuthor.setFocusable(false);
        etGrade.setFocusable(false);
        etDate.setFocusable(false);
        etReview.setFocusable(false);
    }



    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, "Update for the book OK", Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, "Error for the update", Toast.LENGTH_LONG);
            statusToast.show();
        }
    }



    private void switchEditableMode(){
        if(!isEditable){

            etAuthor.setFocusable(true);
            etAuthor.setEnabled(true);
            etAuthor.setFocusableInTouchMode(true);

            etGrade.setFocusable(true);
            etGrade.setEnabled(true);
            etGrade.setFocusableInTouchMode(true);

            etDate.setFocusable(true);
            etDate.setEnabled(true);
            etDate.setFocusableInTouchMode(true);

            etReview.setFocusable(true);
            etReview.setEnabled(true);
            etReview.setFocusableInTouchMode(true);


        }else{
            saveChanges(
                    etAuthor.getText().toString(),
                    Double.parseDouble(etGrade.getText().toString()),
                    etDate.getText().toString(),
                    etReview.getText().toString()
            );

            etAuthor.setFocusable(false);
            etAuthor.setEnabled(false);

            etGrade.setFocusable(false);
            etGrade.setEnabled(false);

            etDate.setFocusable(false);
            etDate.setEnabled(false);

            etReview.setFocusable(false);
            etReview.setEnabled(false);

        }
        isEditable = !isEditable;
    }

    private void saveChanges(String author, double grade, String date, String reviewText){
        review.setAuthor(author);
        review.setGrade(grade);
        review.setDate(date);
        review.setReview(reviewText);

        viewModel.updateReview(review, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateBook: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateBook: failure", e);
                setResponse(false);
            }
        });
    }




    /** Called when click on update review*/
    public void updateReview(View v) {
        switchEditableMode();
    }

    /** Called when click on delete book **/
    public void deleteReview(View v){
        viewModel.deleteReview(review, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "deleteReview: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "delteReview: failure", e);
                setResponse(false);
            }
        });
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
