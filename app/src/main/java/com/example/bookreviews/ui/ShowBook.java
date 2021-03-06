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
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BookViewModel;


public class ShowBook extends AppCompatActivity {

    private static final String TAG = "BookDetails";
    private BookViewModel viewModel;
    private BookEntity book;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etTitle;
    private EditText etAuthor;
    private EditText etCategory;
    private EditText etEdition;
    private EditText etYearPublished;
    private EditText etPlotSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_display_book);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //get datas of the selected book
        String bookId = getIntent().getStringExtra("bookId");
        String bookTitle = getIntent().getStringExtra("bookTitle");
        String bookAuthor = getIntent().getStringExtra("bookAuthor");
        String bookCategory = getIntent().getStringExtra("bookCategory");
        String bookEdition = getIntent().getStringExtra("bookEdition");
        String bookPlotSummary = getIntent().getStringExtra("bookPlotSummary");
        int bookYearPublished = getIntent().getIntExtra("bookYearPublished", 0);

        //create the book entity
        book = new BookEntity(bookTitle,bookAuthor,bookEdition,bookCategory,bookYearPublished,bookPlotSummary);
        book.setId(bookId);

        initiateView();

        //create the book to display
        BookViewModel.Factory factory = new BookViewModel.Factory(getApplication(), bookId,
                bookTitle,bookAuthor,bookEdition,bookCategory,bookYearPublished,bookPlotSummary);
        viewModel = ViewModelProviders.of(this, factory).get(BookViewModel.class);
        viewModel.getBook().observe(this, bookEntity -> {
            if(bookEntity != null){
                book = bookEntity;

            }
        });

    }

    //initialize the view of the book with the datas we have at first
    private void initiateView(){
        isEditable = false;
        etTitle = findViewById(R.id.title);
        etTitle.setText(book.getTitle());
        etAuthor = findViewById(R.id.author);
        etAuthor.setText(book.getAuthor());
        etCategory = findViewById(R.id.category);
        etCategory.setText(book.getCategory());
        etEdition = findViewById(R.id.edition);
        etEdition.setText(book.getEdition());
        etYearPublished = findViewById(R.id.yearPublished);
        etYearPublished.setText(String.valueOf(book.getYearPublished()));
        etPlotSummary = findViewById(R.id.plotSummary);
        etPlotSummary.setText(book.getPlotSummary());

        etTitle.setFocusable(false);
        etAuthor.setFocusable(false);
        etCategory.setFocusable(false);
        etEdition.setFocusable(false);
        etYearPublished.setFocusable(false);
        etPlotSummary.setFocusable(false);
    }

    //switch the fields in editable mode to modify them
    private void switchEditableMode(){
        if(!isEditable){
            etTitle.setFocusable(true);
            etTitle.setEnabled(true);
            etTitle.setFocusableInTouchMode(true);

            etAuthor.setFocusable(true);
            etAuthor.setEnabled(true);
            etAuthor.setFocusableInTouchMode(true);

            etCategory.setFocusable(true);
            etCategory.setEnabled(true);
            etCategory.setFocusableInTouchMode(true);

            etEdition.setFocusable(true);
            etEdition.setEnabled(true);
            etEdition.setFocusableInTouchMode(true);

            etYearPublished.setFocusable(true);
            etYearPublished.setEnabled(true);
            etYearPublished.setFocusableInTouchMode(true);

            etPlotSummary.setFocusable(true);
            etPlotSummary.setEnabled(true);
            etPlotSummary.setFocusableInTouchMode(true);

            etTitle.requestFocus();
        }else{
            //if the fields are in editable mode, put them back into view mode and save the changes in DB
            saveChanges(
                    etTitle.getText().toString(),
                    etAuthor.getText().toString(),
                    etCategory.getText().toString(),
                    etEdition.getText().toString(),
                    Integer.parseInt(etYearPublished.getText().toString()),
                    etPlotSummary.getText().toString()
            );
            etTitle.setFocusable(false);
            etTitle.setEnabled(false);

            etAuthor.setFocusable(false);
            etAuthor.setEnabled(false);

            etCategory.setFocusable(false);
            etCategory.setEnabled(false);

            etEdition.setFocusable(false);
            etEdition.setEnabled(false);

            etYearPublished.setFocusable(false);
            etYearPublished.setEnabled(false);

            etPlotSummary.setFocusable(false);
            etPlotSummary.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    //save the modified datas in the DB
    private void saveChanges(String title, String author, String category, String edition,
                             int yearPublished, String plotSummary){
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setEdition(edition);
        book.setYearPublished(yearPublished);
        book.setPlotSummary(plotSummary);

        viewModel.updateBook(book, new OnAsyncEventListener() {
            //write in the LOG if the book has been updated or not
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

    //Display a little message if the book has been updated or not
    private void setResponse(Boolean response) {

        if (response) {
            statusToast = Toast.makeText(this, "Update for the book OK", Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, "Error for the update", Toast.LENGTH_LONG);
            statusToast.show();
        }

    }

    private void updateContent(){
        if(book != null){
            etTitle.setText(book.getTitle());
            etAuthor.setText(book.getAuthor());
            etCategory.setText(book.getCategory());
            etEdition.setText(book.getEdition());
            etYearPublished.setText(book.getYearPublished());
            etPlotSummary.setText(book.getPlotSummary());
        }
    }

    /** Called when click on display Reviews*/
    public void displayAllReviews(View view) {
        Intent intent = new Intent(this, ShowAllReviews.class);
        intent.putExtra("bookId", book.getId());
        intent.putExtra("bookTitle", book.getTitle());
        startActivity(intent);
    }

    /** Called when click on update book*/
    public void updateBook(View v) {
        switchEditableMode();
    }

    /** Called when click on delete book **/
    public void deleteBook(View v){
        viewModel.deleteBook(book, new OnAsyncEventListener() {
            //write in the LOG if the book has been deleted or not
            @Override
            public void onSuccess() {
                Log.d(TAG, "deleteBook: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "delteBook: failure", e);
                setResponse(false);
            }
        });
    }

    /** Called when click on display Reviews*/
    public void addReview(View view) {
        Intent intent = new Intent(this, AddReview.class);
        intent.putExtra("bookTitle", book.getTitle());
        intent.putExtra("bookId", book.getId());
        startActivity(intent);
    }

    //Button displaying little navigation menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_default_menu, menu);
        return true;
    }

    //choose to go to settings or another page
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
