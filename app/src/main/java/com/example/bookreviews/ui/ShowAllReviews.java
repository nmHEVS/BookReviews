package com.example.bookreviews.ui;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviews.R;
import com.example.bookreviews.adapter.RecyclerAdapter;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.database.repository.ReviewRepository;
import com.example.bookreviews.util.RecyclerViewItemClickListener;
import com.example.bookreviews.viewmodel.BookListViewModel;
import com.example.bookreviews.viewmodel.ReviewListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowAllReviews extends AppCompatActivity {

    private static final String TAG = "ReviewsActivity";

    private List<ReviewEntity> reviews;
    private RecyclerAdapter<ReviewEntity> adapter;
    private ReviewListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set light or dark mode
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme);
        else
            setTheme(R.style.LightTheme);

        setContentView(R.layout.activity_display_all_reviews);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Long bookId = getIntent().getLongExtra("bookId", 0L);
        String bookTitle = getIntent().getStringExtra("bookTitle");

        RecyclerView recyclerView = findViewById(R.id.reviewsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        reviews = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position){
                Intent intent = new Intent(ShowAllReviews.this, ShowReview.class);
                intent.putExtra("reviewId", reviews.get(position).getId());
                intent.putExtra("reviewId_book", reviews.get(position).getId_book());
                intent.putExtra("reviewAuthor", reviews.get(position).getAuthor());
                intent.putExtra("reviewGrade", reviews.get(position).getGrade());
                intent.putExtra("reviewDate", reviews.get(position).getDate());
                intent.putExtra("reviewReview", reviews.get(position).getReview());
                intent.putExtra("reviewBookTitle", bookTitle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position){
                Intent intent = new Intent(ShowAllReviews.this, ShowReview.class);
                intent.putExtra("reviewId", reviews.get(position).getId());
                intent.putExtra("reviewId_book", reviews.get(position).getId_book());
                intent.putExtra("reviewAuthor", reviews.get(position).getAuthor());
                intent.putExtra("reviewGrade", reviews.get(position).getGrade());
                intent.putExtra("reviewReview", reviews.get(position).getReview());
                startActivity(intent);
            }
        });


        viewModel = new ReviewListViewModel(getApplication(),bookId, ReviewRepository.getInstance());

        viewModel.getReviewsByIdBook().observe(this, reviewEntities -> {
            if(reviewEntities!= null){
                reviews = reviewEntities;
                adapter.setData(reviews);
            }
        });
        recyclerView.setAdapter(adapter);

    }


    /** Called when click on the book to display*/
    public void displayReview(View view) {
        Intent intent = new Intent(this, ShowReview.class);
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
