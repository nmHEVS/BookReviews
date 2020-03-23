package com.example.bookreviews.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.AppDatabase;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;

import java.util.List;
import java.util.WeakHashMap;

public class ReviewRepository {

    private static ReviewRepository instance;

    private ReviewRepository() {}

    public static ReviewRepository getInstance(){
        if(instance == null){
            synchronized (BookRepository.class){
                if(instance == null){
                    instance = new ReviewRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<String>> getAllAuthorForABook(final long id, Context context){
        return AppDatabase.getInstance(context).reviewDao().getByIdBook(id);
    }

    public LiveData<ReviewEntity> getReview(final long id_book, final String author, Context context){
        return AppDatabase.getInstance(context).reviewDao().getReview(id_book, author);
    }

    public LiveData<List<String>> getAvgGrade(final long id_book, Context context){
        return AppDatabase.getInstance(context).reviewDao().getAvgGrade(id_book);
    }

}
