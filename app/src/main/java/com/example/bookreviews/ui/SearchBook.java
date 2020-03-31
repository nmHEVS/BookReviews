package com.example.bookreviews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviews.R;
import com.example.bookreviews.adapter.RecyclerAdapter;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.util.RecyclerViewItemClickListener;
import com.example.bookreviews.viewmodel.BookListViewModel;
import com.example.bookreviews.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchBook extends AppCompatActivity {

    private List<BookEntity> books;
    private RecyclerAdapter<BookEntity> recyclerAdapter;
    private BookListViewModel viewModel;

    private SearchView etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_search_book);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        etSearch = findViewById(R.id.searchView);

        Spinner spinner = (Spinner) findViewById(R.id.search_by_spinner);
        //Create array adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_by_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply adapter to spinner
        spinner.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.booksRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        books = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position){
                Intent intent = new Intent(SearchBook.this, ShowBook.class);
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
                Intent intent = new Intent(SearchBook.this, ShowBook.class);
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

        recyclerView.setAdapter(recyclerAdapter);

        BookListViewModel.Factory factory = new BookListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(BookListViewModel.class);
        viewModel.getBooks().observe(this, bookEntities -> {
            if(bookEntities!= null){
                books = bookEntities;
                recyclerAdapter.setData(books);
            }
        });

        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_search_book_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, ChangeSettings.class);
                startActivity(intent);
                return true;
            case R.id.action_allbooks:
                intent = new Intent(this, ShowAllBooks.class);
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
