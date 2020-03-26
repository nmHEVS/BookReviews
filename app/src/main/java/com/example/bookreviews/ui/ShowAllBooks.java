package com.example.bookreviews.ui;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviews.R;
import com.example.bookreviews.adapter.RecyclerAdapter;
import com.example.bookreviews.database.entity.BookEntity;
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

        RecyclerView recyclerView = findViewById(R.id.booksRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        books = new ArrayList<>();


        setContentView(R.layout.activity_display_all_books);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final String[] books = getResources().getStringArray(R.array.allbooks_array);
        ListView list;

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.listview_books_layout, books){
            //Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view;
                //If view doesn't exist create a new view
                if(convertView==null){
                    //Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.listview_books_layout, parent, false);
                } else{
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.allbooks_array);
                textView1.setText(books[position]);

                return view;
            }
        };

        //ListView
        /*list = (ListView) findViewById(R.id.allbooks_array);
        list.setAdapter(adapter);

        ListView handler
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                displayBook(view);
            }
        });*/
        //Show list of all books


    }

    /** Called when click on the book to display*/
    public void displayBook(View view) {
        Intent intent = new Intent(this, ShowBook.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_all_books_menu, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
