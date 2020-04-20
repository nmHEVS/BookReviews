package com.example.bookreviews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviews.R;
import com.example.bookreviews.adapter.RecyclerAdapter;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.repository.BookRepository;
import com.example.bookreviews.util.RecyclerViewItemClickListener;
import com.example.bookreviews.viewmodel.BookListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowAllBooks extends AppCompatActivity {

    private static final String TAG = "BooksActivity";

    private List<BookEntity> books;
    private RecyclerAdapter<BookEntity> adapter;
    private BookListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_display_all_books);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //create and set the layout for the recycler view to display the datas
        RecyclerView recyclerView = findViewById(R.id.booksRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //create the onClick objets that display the datas
        books = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position){
                Intent intent = new Intent(ShowAllBooks.this, ShowBook.class);
                intent.putExtra("bookId", books.get(position).getId());
                intent.putExtra("bookTitle", books.get(position).getTitle());
                intent.putExtra("bookAuthor", books.get(position).getAuthor());
                intent.putExtra("bookCategory", books.get(position).getCategory());
                intent.putExtra("bookEdition", books.get(position).getEdition());
                intent.putExtra("bookPlotSummary", books.get(position).getPlotSummary());
                intent.putExtra("bookYearPublished", books.get(position).getYearPublished());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position){
                Intent intent = new Intent(ShowAllBooks.this, ShowBook.class);
                intent.putExtra("bookId", books.get(position).getId());
                intent.putExtra("bookTitle", books.get(position).getTitle());
                intent.putExtra("bookAuthor", books.get(position).getAuthor());
                intent.putExtra("bookCategory", books.get(position).getCategory());
                intent.putExtra("bookEdition", books.get(position).getEdition());
                intent.putExtra("bookPlotSummary", books.get(position).getPlotSummary());
                intent.putExtra("bookYearPublished", books.get(position).getYearPublished());
                startActivity(intent);
            }
        });

        //Create the list of books to display the titles
        BookListViewModel.Factory factory = new BookListViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider(this, factory).get(BookListViewModel.class);
        viewModel.getBooks().observe(this, bookEntities -> {
            if(bookEntities!= null){
                books = bookEntities;
                adapter.setData(books);
            }
        });

        recyclerView.setAdapter(adapter);

    }

    /** Called when click on the book to display*/
    public void displayBook(View view) {
        Intent intent = new Intent(this, ShowBook.class);
        startActivity(intent);
    }

    //Button displaying little navigation menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_all_books_menu, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
