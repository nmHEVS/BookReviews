package com.example.bookreviews.viewmodel;

import android.app.Application;

import com.example.bookreviews.database.AppDatabase;
import com.example.bookreviews.database.repository.BookRepository;
import com.example.bookreviews.database.repository.ReviewRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this);
    }

    public BookRepository getBookRepository(){
        return BookRepository.getInstance();
    }

    public ReviewRepository getReviewRepository(){
        return ReviewRepository.getInstance();
    }
}
